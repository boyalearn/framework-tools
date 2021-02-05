package com.frameworktools.jackson.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {

    private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");


    public static final boolean isNumber(String number){
        Matcher isNum = NUMBER_PATTERN.matcher(number);
        if (isNum.matches()) {
            return true;
        }
        return false;
    }
}
