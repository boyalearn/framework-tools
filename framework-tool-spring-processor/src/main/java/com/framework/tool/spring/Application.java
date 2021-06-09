package com.framework.tool.spring;

import com.framework.tool.spring.annonation.ServiceScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@ServiceScan("com.framework.tool.spring")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
