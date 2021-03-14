package com.frameworktool.test.config;

import com.frameworktool.basetype.RespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RespResult errorHandler(Exception ex) {
        log.error("error message", ex);
        return new RespResult.Builder().code(500).message(ex.getMessage()).build();
    }
}
