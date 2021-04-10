package com.framework.websocket.core.server;

import com.framework.websocket.core.config.ServerConfig;
import com.framework.websocket.core.context.ServerContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


public class WebSocketServer implements BeanDefinitionRegistryPostProcessor {

    public void WebSocketServer(ServerConfig serverConfig) {
        serverConfig.parserConfig();
        ServerContext.setEventPublisher(serverConfig.getPublisher());
    }

    public WebSocketServer() {
        ServerConfig serverConfig = new ServerConfig.Builder().build();
        serverConfig.parserConfig();
        ServerContext.setEventPublisher(serverConfig.getPublisher());
    }


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(ServerEndpointExporter.class);
        beanDefinitionRegistry.registerBeanDefinition("serverEndpointExporter", rootBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        return;
    }
}
