package com.frameworktool.test.controller;

import com.frameworktool.basetype.RespResult;
import com.frameworktool.test.entity.EnumEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping
public class DemoController {

    @GetMapping("/index")
    public EnumEntity getIndex(EnumEntity enumEntity, HttpServletResponse response) {
        response.addHeader("dfs", "dfs");
        return enumEntity;
    }

    @PostMapping("/index")
    public RespResult postIndex(@RequestBody(required = false) EnumEntity enumEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(enumEntity.getName())) {
            throw new Exception("this is a exception");
        }

        return new RespResult.Builder().data(enumEntity).build();
    }
}
