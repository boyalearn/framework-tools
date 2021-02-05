package com.frameworktool.webmvc.converter;

import com.frameworktool.basetype.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.StringUtils;

public class NumberEnumConverterFactory implements ConverterFactory<String, IEnum> {
    @Override
    public <T extends IEnum> Converter<String, T> getConverter(Class<T> aClass) {
        return new StringToEnumConverter<>(aClass);
    }

    private static class StringToEnumConverter<T extends IEnum> implements Converter<String, T> {

        private Class<T> targetType;

        public StringToEnumConverter(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            if (StringUtils.isEmpty(source)) {
                return null;
            }
            if (!targetType.isEnum()) {
                return null;
            }
            T[] enumConstants = targetType.getEnumConstants();
            for (T t : enumConstants) {
                if (Integer.valueOf(source).equals(t.getIndex())) {
                    return t;
                }
            }
            return null;

        }
    }
}
