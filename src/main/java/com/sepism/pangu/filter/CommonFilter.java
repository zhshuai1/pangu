package com.sepism.pangu.filter;


import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.*;
import java.io.IOException;
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
        filterChain.doFilter(servletRequest, servletResponse);
        ThreadContext.clearAll();
    }

    public void destroy() {

    }
}
