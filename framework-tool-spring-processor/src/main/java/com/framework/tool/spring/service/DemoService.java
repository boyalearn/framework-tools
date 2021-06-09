package com.framework.tool.spring.service;

import com.framework.tool.spring.annonation.Service;

import javax.annotation.PostConstruct;

@Service
public class DemoService {
    public void sayHello() {
        System.out.println("Hello");
    }

    @PostConstruct
    public void init(){
        System.out.println("init");
    }
}
