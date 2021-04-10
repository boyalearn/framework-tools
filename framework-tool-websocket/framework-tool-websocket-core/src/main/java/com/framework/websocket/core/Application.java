package com.framework.websocket.core;

import com.framework.websocket.core.config.ServerConfig;
import com.framework.websocket.core.server.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebSocketServer WebSocketServer() {
        new ServerConfig.Builder().handlers(null).build();
        return new WebSocketServer();
    }
}
