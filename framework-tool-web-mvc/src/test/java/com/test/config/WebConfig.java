package com.test.config;

import com.framework.tool.adapter.WebMvcConfigurerAdapter;
import com.framework.tool.resolver.WebHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurerAdapter(new WebHandlerMethodArgumentResolver());
    }
}
