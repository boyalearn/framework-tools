package com.test.controller;

import com.framework.tool.annotation.IncludeParameter;
import com.framework.tool.annotation.ParameterParser;
import com.framework.tool.annotation.RequestJson;
import com.test.entity.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    @IncludeParameter({"name","age"})
    @ParameterParser(key = "name", method = "setName")
    public Param test(@RequestJson Param param) {
        Param response = new Param();
        response.setAge(18);
        response.setName("小明");
        return response;
    }
}
