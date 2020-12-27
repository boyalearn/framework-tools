package com.test.controller;

import com.framework.tool.annotation.*;
import com.test.entity.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    @IncludeParameter({"name","age"})
    @ParameterParser(key = "name", method = "setName")
    @ResponseJson
    public Param test(@RequestJson Param param) {
        return param;
    }

    @PostMapping("/test")
    @IncludeParameter({"name","age"})
    @ParameterParser(key = "name", method = "setName")
    @ResponseJson
    @IncludeResponse({"name"})
    public Param testPost(@RequestJson Param param) {
        return param;
    }
}
