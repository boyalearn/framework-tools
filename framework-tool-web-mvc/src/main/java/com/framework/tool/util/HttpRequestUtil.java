package com.framework.tool.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

public final class HttpRequestUtil {

    public static Object getParameterByHttpRequest(String key, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if(key.equals(headerName)) {
                return request.getHeader(headerName);
            }
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if(key.equals(parameterName)) {
                return request.getParameter(parameterName);
            }
        }

        return null;
    }
}
