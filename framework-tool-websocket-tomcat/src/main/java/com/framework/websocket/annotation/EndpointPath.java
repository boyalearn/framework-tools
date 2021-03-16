package com.framework.websocket.annotation;

public @interface EndpointPath {
    String value() default "";
}
