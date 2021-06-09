package com.framework.tool.spring.service;

import com.framework.tool.spring.annonation.Reference;
import com.framework.tool.spring.annonation.Service;

import javax.annotation.PostConstruct;

@Service
public class DependenceService {

    @Reference
    private DemoService demoService;

    @PostConstruct
    public void init(){
        System.out.println("do demo service");
        demoService.init();
    }
}
