package com.framework.tool.annotation;

public @interface Parameter {
    String key() default "";
    String method() default "";
}
