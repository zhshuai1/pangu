package com.sepism.pangu.filter;


import com.sepism.pangu.constant.CookieName;
import com.sepism.pangu.constant.RequestAttribute;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

@Log4j2
public class CommonFilter implements Filter {
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        ThreadContext.push(UUID.randomUUID().toString());
        log.info("One request to visit sepism.com! RemoteHost: {}, RemoteAddress: {}, RemotePort: {}",
                servletRequest.getRemoteHost(), servletRequest.getRemoteAddr(), servletRequest.getRemotePort());
        parseCookiesToAttributes(servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
        ThreadContext.clearAll();
    }

    private void parseCookiesToAttributes(ServletRequest servletRequest) {
        Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();
        servletRequest.setAttribute(RequestAttribute.LOCALE, new Locale(Locale.CHINESE.getLanguage()));
        // The Nullable check is for browser compatibility; for safari, no need to to this check. But for firefox,
        // QQ and 360, the check is necessary.
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                String cookieValue = cookie.getValue();
                log.debug("CookieName is [{}] and cookieValue is [{}]", cookieName, cookieValue);
                if (CookieName.SIMPLE_COOKIE_NAME.contains(cookieName)) {
                    servletRequest.setAttribute(cookieName, cookieValue);
                    continue;
                }
                switch (cookieName) {
                    case CookieName.LOCALE:
                        try {
                            servletRequest.setAttribute(RequestAttribute.LOCALE, new Locale(cookieValue));
                        } catch (Exception e) {
                            log.warn("Failed to create locale for {}", cookieValue);
                        }
                        break;
                    default:
                        break;
                }

            }
        }
    }

    public void destroy() {

    }
}
