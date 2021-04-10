package com.framework.websocket.spring.config;

import com.framework.websocket.core.config.ServerConfig;
import com.framework.websocket.core.context.ServerContext;
import com.framework.websocket.core.handler.Handler;
import com.framework.websocket.spring.annonation.Command;
import com.framework.websocket.spring.annonation.WebSocketController;
import com.framework.websocket.spring.handler.HandlerInvoker;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class WebSocketConfigRegisterBean implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;


    @Override
    public void afterPropertiesSet() throws Exception {

        String[] webSocketControllerNames = applicationContext.getBeanNamesForAnnotation(WebSocketController.class);

        List<Handler> handlerList=new ArrayList<>();

        for (String webSocketControllerName : webSocketControllerNames) {
            Object bean = applicationContext.getBean(webSocketControllerName);
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                Command command = method.getAnnotation(Command.class);
                if (null != command) {
                    HandlerInvoker handlerInvoker=new HandlerInvoker(bean,method,method.getParameterTypes(),command.value());
                    handlerList.add(handlerInvoker);
                }
            }
        }

        ServerConfig serverConfig = new ServerConfig.Builder().handlers(handlerList).build();
        serverConfig.parserConfig();
        ServerContext.setEventPublisher(serverConfig.getPublisher());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
