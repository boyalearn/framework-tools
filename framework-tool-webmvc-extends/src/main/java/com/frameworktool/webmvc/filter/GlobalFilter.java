package com.frameworktool.webmvc.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.apache.catalina.core.StandardContext;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;

@Slf4j
public class GlobalFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        try {
            Field contextField = ApplicationFilterConfig.class.getDeclaredField("context");
            contextField.setAccessible(true);
            StandardContext context = (StandardContext) contextField.get(filterConfig);
            FilterMap[] filterArray = context.findFilterMaps();
            for (int i = 0; i < filterArray.length; i++) {
                if (filterConfig.getFilterName().equals(filterArray[i].getFilterName())) {
                    FilterMap temp = new FilterMap();
                    temp = filterArray[0];
                    filterArray[0] = filterArray[i];
                    filterArray[i] = filterArray[0];
                    break;
                }
            }
            contextField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            log.error("No Such Method", e);
        } catch (IllegalAccessException e) {
            log.error("Illegal Access", e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        TraceRequestWrapper traceRequestWrapper = new TraceRequestWrapper((HttpServletRequest) servletRequest);
        System.out.println(traceRequestWrapper.getHeader());
        System.out.println(traceRequestWrapper.getBody());


        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.getOutputStream();
        TraceResponseWrapper responseWrapper = new TraceResponseWrapper(httpServletResponse);
        filterChain.doFilter(servletRequest, responseWrapper);
        System.out.println(httpServletResponse.getStatus());
        System.out.println(new String(responseWrapper.getBytes()));
        System.out.println(responseWrapper.getHeaders());

    }

    @Override
    public void destroy() {
        System.out.println("LogFilter destroy");
    }
}
