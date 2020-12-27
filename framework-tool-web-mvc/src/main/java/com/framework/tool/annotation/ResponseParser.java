package com.framework.tool.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseParser {
    String key() default "";
    String method() default "";
}
