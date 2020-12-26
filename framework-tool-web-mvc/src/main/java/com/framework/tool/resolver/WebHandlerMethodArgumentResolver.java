package com.framework.tool.resolver;

import com.framework.tool.annotation.RequestJson;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class WebHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestJson.class);
    }

    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        RequestJson requestJson = methodParameter.getParameterAnnotation(RequestJson.class);
        Class<?> parameterType = methodParameter.getParameterType();
        Object parameter=parameterType.newInstance();
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        return null;
    }
}
