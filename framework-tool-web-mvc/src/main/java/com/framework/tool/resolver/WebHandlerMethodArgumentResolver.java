package com.framework.tool.resolver;

import com.framework.tool.annotation.ExcludeParameter;
import com.framework.tool.annotation.IncludeParameter;
import com.framework.tool.annotation.ParameterParser;
import com.framework.tool.annotation.RequestJson;
import com.framework.tool.util.ClassDecoratorUtil;
import com.framework.tool.util.HttpRequestUtil;
import com.framework.tool.util.MethodUtil;
import com.framework.tool.util.StringUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WebHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final ConcurrentHashMap<Method, ParameterDefinition> PARAMETER_DEFINITION = new ConcurrentHashMap<>();

    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestJson.class);
    }

    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Method method = methodParameter.getMethod();
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        return buildParameter(method, request, methodParameter);
    }

    private Object buildParameter(Method method, HttpServletRequest request, MethodParameter methodParameter)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {

        ParameterDefinition parameterDefinition = PARAMETER_DEFINITION.get(method);
        if (null == parameterDefinition) {
            parameterDefinition = resolveParameterDefinition(method, methodParameter);
        }

        return buildParameterByParameterDefinition(parameterDefinition, request);
    }

    private Object buildParameterByParameterDefinition(ParameterDefinition parameterDefinition, HttpServletRequest request)
            throws IllegalAccessException, InstantiationException, InvocationTargetException {

        Object object = parameterDefinition.getType().newInstance();
        List<FieldDefinition> fieldDefinitions = parameterDefinition.getFieldDefinitions();
        for (FieldDefinition fieldDefinition : fieldDefinitions) {
            fieldDefinition.invokeSetMethod(object, HttpRequestUtil.getParameterByHttpRequest(fieldDefinition.getName(), request));
        }
        return object;
    }


    private ParameterDefinition resolveParameterDefinition(Method method, MethodParameter methodParameter) {
        ParameterDefinition parameterDefinition = new ParameterDefinition();

        Class<?> parameterType = methodParameter.getParameterType();
        parameterDefinition.setType(parameterType);

        ParameterParser[] parameterParsers = method.getAnnotationsByType(ParameterParser.class);


        IncludeParameter[] includeParameters = method.getAnnotationsByType(IncludeParameter.class);
        if (includeParameters.length > 0) {
            for (IncludeParameter includeParameter : includeParameters) {
                for (String key : includeParameter.value()) {
                    parameterDefinition.getFieldDefinitions().add(parserField(key, parameterParsers, parameterType));
                }
            }
            return parameterDefinition;
        }

        ExcludeParameter[] excludeParameters = method.getAnnotationsByType(ExcludeParameter.class);
        Field[] declaredFields = parameterType.getDeclaredFields();
        for (Field field : declaredFields) {
            if (isExcludeParameter(field, excludeParameters)) {
                continue;
            }
            parameterDefinition.getFieldDefinitions().add(parserField(field.getName(), parameterParsers, parameterType));
        }

        return parameterDefinition;
    }

    private boolean isExcludeParameter(Field field, ExcludeParameter[] excludeParameters) {
        for (ExcludeParameter excludeParameter : excludeParameters) {
            for (String exclude : excludeParameter.value()) {
                if (field.getName().equals(exclude)) {
                    return true;
                }
            }
        }
        return false;
    }

    private FieldDefinition parserField(String key, ParameterParser[] parameterParsers, Class<?> parameterType) {
        FieldDefinition field = new FieldDefinition();
        field.setName(key);
        for (ParameterParser parameterParser : parameterParsers) {
            if (parameterParser.key().equals(key)) {
                field.setSetMethod(MethodUtil.getMethodByName(parameterType, parameterParser.method()));
                field.setSetParameterType(ClassDecoratorUtil.buildClassDecorator(field.getSetMethod().getParameterTypes()[0]));
                return field;
            }
        }
        field.setSetMethod(MethodUtil.getMethodByName(parameterType, "set" + StringUtil.captureName(key)));
        field.setSetParameterType(ClassDecoratorUtil.buildClassDecorator(field.getSetMethod().getParameterTypes()[0]));
        return field;
    }
}
