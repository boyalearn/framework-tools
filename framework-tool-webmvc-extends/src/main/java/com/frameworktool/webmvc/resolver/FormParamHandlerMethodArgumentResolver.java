package com.frameworktool.webmvc.resolver;


import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public class FormParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(FormParamBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Parameter parameter = methodParameter.getParameter();
        Class<?> type = parameter.getType();
        return buildParameter(type, nativeWebRequest, webDataBinderFactory);
    }

    public Object buildParameter(Class<?> pClass, NativeWebRequest request, WebDataBinderFactory webDataBinderFactory) throws IllegalAccessException, InstantiationException {
        Object param = pClass.newInstance();
        Field[] fields = pClass.getDeclaredFields();
        for (Field field : fields) {
            FormKey keyName = field.getAnnotation(FormKey.class);
            String formKeyName = field.getName();
            if (null != keyName) {
                formKeyName = keyName.value();
            }
            String value = request.getParameter(formKeyName);
            if (null != value) {
                field.setAccessible(true);
                try {
                    WebDataBinder binder = webDataBinderFactory.createBinder(request, value, field.getName());
                    Object data = binder.convertIfNecessary(value, field.getType());
                    field.set(param, field.getType().cast(data));
                } catch (Exception e) {
                    throw new RuntimeException("解析参数失败");
                }
                field.setAccessible(false);
            }
        }
        return param;

    }

}
