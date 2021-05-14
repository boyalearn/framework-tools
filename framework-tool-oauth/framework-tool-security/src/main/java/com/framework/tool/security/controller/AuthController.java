package com.framework.tool.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    public AuthController() {
        System.out.println("HHHHHHHH");
    }

    @GetMapping("/home")
    public String home() {
        return "hello";
    }

    @Secured("ADMIN")
    @GetMapping("/auth")
    public String auth() {
        return "auth";
    }
}
