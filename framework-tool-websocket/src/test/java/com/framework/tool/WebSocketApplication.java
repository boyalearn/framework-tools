package com.framework.tool;

import com.framework.tool.websocket.DefaultEndpoint;
import com.framework.tool.websocket.WebSocketEndpointConfigBean;
import com.framework.tool.websocket.WebSocketEndpointExporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
        return webSocketEndpointConfigBean;
    }
}
