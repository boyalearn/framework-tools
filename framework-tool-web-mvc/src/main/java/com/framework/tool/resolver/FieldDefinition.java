package com.framework.tool.resolver;

import com.framework.tool.decorator.ClassDecorator;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Getter
@Setter
public class FieldDefinition {

    private String name;

    private Method setMethod;

    private ClassDecorator setParameterType;

    public void invokeSetMethod(Object object, Object parameter) throws InvocationTargetException, IllegalAccessException {
        setMethod.invoke(object, setParameterType.cast(parameter));
    }
}
