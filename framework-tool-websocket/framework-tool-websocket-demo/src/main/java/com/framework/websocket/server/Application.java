package com.framework.websocket.server;

import com.broheim.websocket.spring.annonation.EnableWebSocketServer;
import com.broheim.websocket.spring.reactor.CommandReactor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableWebSocketServer
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandReactor commandReactor(){
        return new CommandReactor();
    }
}
