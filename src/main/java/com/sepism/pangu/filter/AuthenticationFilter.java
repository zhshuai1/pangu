package com.sepism.pangu.filter;


import com.sepism.pangu.constant.CookieName;
import com.sepism.pangu.constant.GlobalConstant;
import com.sepism.pangu.constant.RequestAttribute;
import com.sepism.pangu.model.authentication.Session;
import com.sepism.pangu.model.repository.SessionRepository;
import com.sepism.pangu.util.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

@Log4j2
public class AuthenticationFilter implements Filter {
    private FilterConfig filterConfig;

    @Autowired
    private SessionRepository sessionRepository;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String userId = (String) servletRequest.getAttribute(CookieName.USER);
        String token = (String) servletRequest.getAttribute(CookieName.TOKEN);

        ThreadContext.push(userId);
        //override the attribute to false in case the user could set the attribute to true
        servletRequest.setAttribute(RequestAttribute.LOGGED_IN, false);
        //if USER/TOKEN is blank, then take the user not logged-in, no need to query db
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(token)) {
            //For a simple version here, I only use mysql as the distributed storage. For a long term solution, I
            // should use No-Sql storage such as mongoDB instead. Besides, cache is useful in this case.

            Session session = sessionRepository.findOne(Long.valueOf(userId));
            if (session.getToken().equals(token) && DateUtil.diff(new Date(), session.getLastAccessTime()) <
                    GlobalConstant.SESSION_EXPIRED_TIME) {
                log.info("The user [{}] has been logged into the system with token [{}]", userId, token);
                servletRequest.setAttribute(RequestAttribute.LOGGED_IN, true);
                session.setLastAccessTime(new Date());
                sessionRepository.save(session);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}