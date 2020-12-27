package com.framework.tool.adapter;

import com.framework.tool.handler.WebHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class ReturnValueHandlerMvcConfigurerAdapter implements WebMvcConfigurer {

    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new WebHandlerMethodReturnValueHandler());
    }
}
