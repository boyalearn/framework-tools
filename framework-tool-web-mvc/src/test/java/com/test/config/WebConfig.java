package com.test.config;

import com.framework.tool.adapter.ArgumentResolversMvcConfigurerAdapter;
import com.framework.tool.adapter.ReturnValueHandlerMvcConfigurerAdapter;
import com.framework.tool.resolver.WebHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer webArgumentResolversMvcConfigurer() {
        return new ArgumentResolversMvcConfigurerAdapter(new WebHandlerMethodArgumentResolver());
    }

    @Bean
    public WebMvcConfigurer returnValueHandlerConfigurer() {
        return new ReturnValueHandlerMvcConfigurerAdapter();
    }
}
