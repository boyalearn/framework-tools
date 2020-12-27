package com.framework.tool.adapter;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class ArgumentResolversMvcConfigurerAdapter implements WebMvcConfigurer {

    private HandlerMethodArgumentResolver handlerMethodArgumentResolver;


    public ArgumentResolversMvcConfigurerAdapter(HandlerMethodArgumentResolver handlerMethodArgumentResolver) {
        this.handlerMethodArgumentResolver = handlerMethodArgumentResolver;
    }

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(handlerMethodArgumentResolver);
    }
}
