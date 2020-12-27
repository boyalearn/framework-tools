package com.framework.tool.handler;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

@Getter
@Setter
public class GetFieldDefinition {

    private String field;

    private Method method;
}
