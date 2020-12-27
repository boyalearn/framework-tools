package com.framework.tool.util;

import com.framework.tool.decorator.ClassDecorator;
import com.framework.tool.decorator.IntegerDecorator;
import com.framework.tool.decorator.OtherClassDecorator;

public final class ClassDecoratorUtil {

    public static final ClassDecorator buildClassDecorator(Class<?> clazz) {
        if (Integer.class == clazz) {
            return new IntegerDecorator();
        } else {
            return new OtherClassDecorator(clazz);
        }

    }
}
