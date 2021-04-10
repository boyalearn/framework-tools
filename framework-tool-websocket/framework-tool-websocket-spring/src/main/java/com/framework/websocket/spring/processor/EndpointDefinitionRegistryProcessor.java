package com.framework.websocket.spring.processor;

import com.framework.websocket.core.endpoint.AbstractWebSocketServerEndpoint;
import com.framework.websocket.core.endpoint.EndpointCreator;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

public class EndpointDefinitionRegistryProcessor implements BeanDefinitionRegistryPostProcessor {


    private EndpointCreator endpointCreator = new EndpointCreator();

    public EndpointDefinitionRegistryProcessor() {
    }

    public EndpointDefinitionRegistryProcessor(EndpointCreator endpointCreator) {
        this.endpointCreator = endpointCreator;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

        try {
            Class<AbstractWebSocketServerEndpoint> endpoint = endpointCreator.createEndpoint("/wss");
            RootBeanDefinition beanDefinition = new RootBeanDefinition(endpoint);
            beanDefinitionRegistry.registerBeanDefinition(endpoint.getName(), beanDefinition);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

    }
}
