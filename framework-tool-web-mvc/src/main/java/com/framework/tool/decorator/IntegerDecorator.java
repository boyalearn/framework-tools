package com.framework.tool.decorator;

public class IntegerDecorator implements ClassDecorator {


    @Override
    public Object cast(Object object) {
        if (null == object) {
            return null;
        }
        return Integer.valueOf(object.toString());
    }
}
