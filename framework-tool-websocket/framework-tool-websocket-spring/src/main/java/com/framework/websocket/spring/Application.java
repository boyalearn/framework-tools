package com.framework.websocket.spring;

import com.framework.websocket.spring.annonation.EnableWebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableWebSocketServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}