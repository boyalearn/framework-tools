package com.framework.tool.handler;

import com.framework.tool.annotation.ExcludeResponse;
import com.framework.tool.annotation.IncludeResponse;
import com.framework.tool.annotation.ResponseJson;
import com.framework.tool.annotation.ResponseParser;
import com.framework.tool.json.JsonUtil;
import com.framework.tool.util.MethodUtil;
import com.framework.tool.util.StringUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private static final ConcurrentHashMap<Method, ReturnValueDefinition> RETURN_VALUE_DEFINITION = new ConcurrentHashMap<>();

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.hasMethodAnnotation(ResponseJson.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {

        HttpServletResponse response = (HttpServletResponse) nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        Assert.state(response != null, "No HttpServletResponse");
        ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
        modelAndViewContainer.setRequestHandled(true);

        outputMessage.getBody().write(JsonUtil.objectToJson(buildReturnValue(returnValue, methodParameter)).getBytes());
    }

    private Object buildReturnValue(Object returnValue, MethodParameter methodParameter) throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> result = new HashMap<String, Object>();
        Method method = methodParameter.getMethod();
        ReturnValueDefinition returnValueDefinition = RETURN_VALUE_DEFINITION.get(method);

        if (null == returnValueDefinition) {
            returnValueDefinition = parserReturnValueDefinition(methodParameter);
            RETURN_VALUE_DEFINITION.put(method, returnValueDefinition);
        }

        for (GetFieldDefinition definition : returnValueDefinition.getFieldDefinitions()) {
            result.put(definition.getField(), definition.getMethod().invoke(returnValue, null));
        }
        return result;
    }

    private ReturnValueDefinition parserReturnValueDefinition(MethodParameter methodParameter) {
        ReturnValueDefinition returnValueDefinition = new ReturnValueDefinition();

        Method method = methodParameter.getMethod();
        Class<?> returnType = method.getReturnType();
        ResponseParser[] responseParsers = method.getAnnotationsByType(ResponseParser.class);

        IncludeResponse[] includeResponses = method.getAnnotationsByType(IncludeResponse.class);
        if (includeResponses.length > 0) {
            for (IncludeResponse IncludeResponse : includeResponses) {
                for (String fieldName : IncludeResponse.value()) {
                    returnValueDefinition.getFieldDefinitions().add(parserField(fieldName, responseParsers, returnType));
                }
            }
            return returnValueDefinition;
        }

        ExcludeResponse[] excludeResponses = method.getAnnotationsByType(ExcludeResponse.class);
        Field[] declaredFields = returnType.getDeclaredFields();
        for (Field field : declaredFields) {
            if (excludeField(field, excludeResponses)) {
                continue;
            }
            returnValueDefinition.getFieldDefinitions().add(parserField(field.getName(), responseParsers, returnType));
        }

        return returnValueDefinition;
    }


    private boolean excludeField(Field field, ExcludeResponse[] excludeResponses) {
        for (ExcludeResponse excludeResponse : excludeResponses) {
            for (String key : excludeResponse.value()) {
                if (field.getName().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }


    private GetFieldDefinition parserField(String key, ResponseParser[] ResponseParsers, Class<?> parameterType) {
        GetFieldDefinition field = new GetFieldDefinition();
        field.setField(key);
        for (ResponseParser ResponseParser : ResponseParsers) {
            if (ResponseParser.key().equals(key)) {
                field.setMethod(MethodUtil.getMethodByName(parameterType, ResponseParser.method()));
                return field;
            }
        }
        field.setMethod(MethodUtil.getMethodByName(parameterType, "get" + StringUtil.captureName(key)));
        return field;
    }
}
