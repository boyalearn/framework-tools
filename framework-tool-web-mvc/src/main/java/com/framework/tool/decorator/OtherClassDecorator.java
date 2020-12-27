package com.framework.tool.decorator;

public class OtherClassDecorator implements ClassDecorator {

    private Class<?> clazz;

    public OtherClassDecorator(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object cast(Object object) {
        if (null == object) {
            return null;
        } else {
            return clazz.cast(object);
        }
    }
}
