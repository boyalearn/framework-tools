package com.framework.websocket.test.config;

import com.framework.websocket.core.server.WebSocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.start();
        return new ServerEndpointExporter();
    }
}
