package com.sepism.pangu.filter;


import com.sepism.pangu.constant.CookieName;
import com.sepism.pangu.constant.RequestAttribute;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
        Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();
        servletRequest.setAttribute(RequestAttribute.LOCALE, new Locale(Locale.CHINESE.getLanguage()));
        for (Cookie cookie : cookies) {
            if (CookieName.LOCALE.equals(cookie.getName())) {
                servletRequest.setAttribute(RequestAttribute.LOCALE, new Locale(cookie.getValue()));
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
        ThreadContext.clearAll();
    }

    public void destroy() {

    }
}
