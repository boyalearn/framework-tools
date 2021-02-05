package com.frameworktool.test.controller;

import com.frameworktool.test.entity.EnumEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class DemoController {

    @GetMapping("/index")
    public EnumEntity getIndex(EnumEntity enumEntity){
        return enumEntity;
    }

    @PostMapping("/index")
    public EnumEntity postIndex(@RequestBody EnumEntity enumEntity){
        return enumEntity;
    }
}
