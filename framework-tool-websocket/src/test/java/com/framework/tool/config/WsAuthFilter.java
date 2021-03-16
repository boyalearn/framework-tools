package com.framework.tool.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class WsAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ((HttpServletRequest)servletRequest).getSession();
        HttpServletResponse response=((HttpServletResponse)servletResponse);
        response.getOutputStream().write("sdfsdf".getBytes(StandardCharsets.UTF_8));
        response.flushBuffer();
    }
}
