package com.framework.websoket.test.config;

import com.framework.websocket.DefaultWebSocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class EndpointConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Bean
    public DefaultWebSocketServer defaultWebSocketServer() {
        return new DefaultWebSocketServer();
    }
}
