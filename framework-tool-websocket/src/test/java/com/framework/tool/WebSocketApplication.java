package com.framework.tool;

import com.framework.tool.config.HttpSessionConfigurator;
import com.framework.tool.websocket.endpoint.DefaultEndpoint;
import com.framework.tool.websocket.WebSocketEndpointConfigBean;
import com.framework.tool.websocket.WebSocketEndpointExporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@SpringBootApplication
public class WebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class, args);
    }

    @Bean
    public WebSocketEndpointExporter webSocketEndpointExporter() {
        return new WebSocketEndpointExporter();
    }

    @Bean
    public WebSocketEndpointConfigBean webSocketEndpointConfigBean(){
        WebSocketEndpointConfigBean webSocketEndpointConfigBean = new WebSocketEndpointConfigBean("/ws",new DefaultEndpoint());

        webSocketEndpointConfigBean.setConfigurator(new HttpSessionConfigurator());
        return webSocketEndpointConfigBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                ((HttpServletRequest)request).getSession();
                chain.doFilter(request,response);
            }
        });
        bean.addUrlPatterns("/*");
        return bean;
    }
}
