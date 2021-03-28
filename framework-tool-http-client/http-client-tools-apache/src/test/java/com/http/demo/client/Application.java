package com.http.demo.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "D:\\IdeaWorkSpace\\MyGitHub\\framework-tools\\jssecacerts");
        SpringApplication.run(Application.class, args);
    }
}
