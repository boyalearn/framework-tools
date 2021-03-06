package com.frameworktool.webmvc.filter;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class TraceRequestWrapper {

    private static final String URL_CONN_SYMBOL = "?";

    private HttpServletRequest request;

    public TraceRequestWrapper(HttpServletRequest request) {
        this.request = request;
    }

    public Map<String, Object> getHeader() {

        Map<String, Object> header = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerNme = headerNames.nextElement();
            header.put(headerNme, request.getHeader(headerNme));
        }
        return header;
    }

    public Object getBody() {
        Map<String, Object> body = new HashMap<>();
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String keyName = attributeNames.nextElement();
            body.put(keyName, request.getHeader(keyName));
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.size() > 0) {
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String keyName = parameterNames.nextElement();
                body.put(keyName, request.getParameter(keyName));
            }
        }


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String lineContext = "";
            StringBuffer bodyBuffer = new StringBuffer(1024);
            while ((lineContext = reader.readLine()) != null) {
                bodyBuffer.append(lineContext);
            }
            return bodyBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    public String getRequestURL() {
        String queryString = request.getQueryString();
        if (StringUtils.isEmpty(queryString)) {
            return request.getRequestURL().toString();
        }
        return request.getRequestURL().append(URL_CONN_SYMBOL + queryString).toString();
    }
}
