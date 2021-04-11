package com.framework.websocket.spring.config;

import com.framework.websocket.core.annonation.SocketEndpointPath;
import com.framework.websocket.core.config.ServerConfig;
import com.framework.websocket.core.context.PublisherHolder;
import com.framework.websocket.core.handler.Handler;
import com.framework.websocket.core.reactor.DefaultReactor;
import com.framework.websocket.core.reactor.Reactor;
import com.framework.websocket.spring.annonation.Command;
import com.framework.websocket.spring.annonation.WebSocketController;
import com.framework.websocket.spring.handler.HandlerCreator;
import com.framework.websocket.spring.handler.HandlerInvoker;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class WebSocketConfigRegisterBean implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;


    private HandlerCreator handlerCreator = new HandlerCreator();


    @Override
    public void afterPropertiesSet() throws Exception {

        String[] webSocketControllerNames = applicationContext.getBeanNamesForAnnotation(WebSocketController.class);

        List<Handler> handlerList = new ArrayList<>();

        for (String webSocketControllerName : webSocketControllerNames) {
            Object bean = applicationContext.getBean(webSocketControllerName);
            WebSocketController webSocketController = bean.getClass().getAnnotation(WebSocketController.class);
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                Command command = method.getAnnotation(Command.class);
                if (null != command) {
                    Class<HandlerInvoker> handlerInvokerClass = handlerCreator.modifyHandlerInvoker(webSocketController.value());
                    HandlerInvoker handlerInvoker = handlerInvokerClass.newInstance();
                    handlerInvoker.setObject(bean);
                    handlerInvoker.setMethod(method);
                    handlerInvoker.setArgTypes(method.getParameterTypes());
                    handlerInvoker.setCmd(command.value());
                    handlerList.add(handlerInvoker);
                }
            }
        }

        Reactor reactor = applicationContext.getBean(Reactor.class);
        if (null == reactor) {
            reactor = new DefaultReactor();
        }
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.addReactor(reactor);
        serverConfig.addHandlers(handlerList);
        PublisherHolder.setServerConfig(serverConfig);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
