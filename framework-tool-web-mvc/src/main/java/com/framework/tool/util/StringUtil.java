package com.framework.tool.util;

public final class StringUtil {
    public static final String captureName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
