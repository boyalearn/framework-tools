package com.framework.tool.util;

import java.lang.reflect.Method;

public final class MethodUtil {

    /**
     * 通过方法名称获取方法
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static final Method getMethodByName(Class<?> clazz, String methodName) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }
}
