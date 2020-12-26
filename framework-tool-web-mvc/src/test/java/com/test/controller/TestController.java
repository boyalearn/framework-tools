package com.test.controller;

import com.framework.tool.annotation.Parameter;
import com.framework.tool.annotation.ReqParameter;
import com.framework.tool.annotation.RequestJson;
import com.framework.tool.annotation.RespParameter;
import com.test.entity.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    @ReqParameter({@Parameter(key = "name", method = "setName"), @Parameter(key = "age", method = "setAge")})
    @RespParameter({@Parameter(key = "name", method = "setName"), @Parameter(key = "age", method = "setAge")})
    public Object test(@RequestJson Param param) {
        return "test";
    }
}
