package com.framework.tool.websocket;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.util.Assert;

import javax.servlet.Filter;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;
import javax.websocket.Extension;
import javax.websocket.server.ServerEndpointConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebSocketEndpointConfigBean implements ServerEndpointConfig, BeanFactoryAware {

    private Endpoint endpoint;

    private String path;

    private List<String> subProtocols = new ArrayList<>(0);

    private List<Extension> extensions = new ArrayList<>(0);

    private List<Class<? extends Encoder>> encoders = new ArrayList<>(0);

    private List<Class<? extends Decoder>> decoders = new ArrayList<>(0);

    private final Map<String, Object> userProperties = new HashMap<>(4);

    private ServerEndpointConfig.Configurator configurator;

    private Filter filter;


    public WebSocketEndpointConfigBean(String path, Endpoint endpoint) {
        this.endpoint = endpoint;
        this.path = path;
        subProtocols.add("token");
    }


    public void setConfigurator(ServerEndpointConfig.Configurator configurator) {
        this.configurator = configurator;
    }


    public void setFilter(Filter filter) {
        Assert.notNull(filter, "Filter must not be null");
        this.filter = filter;
    }


    @Override
    public Class<?> getEndpointClass() {
        return endpoint.getClass();
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public List<String> getSubprotocols() {
        return subProtocols;
    }

    @Override
    public List<Extension> getExtensions() {
        return extensions;
    }

    @Override
    public Configurator getConfigurator() {
        return configurator;
    }

    @Override
    public List<Class<? extends Encoder>> getEncoders() {
        return encoders;
    }

    @Override
    public List<Class<? extends Decoder>> getDecoders() {
        return decoders;
    }

    @Override
    public Map<String, Object> getUserProperties() {
        return userProperties;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (null != filter) {
            FilterRegistrationBean bean = new FilterRegistrationBean();
            bean.setFilter(filter);
            bean.addUrlPatterns(path);
            ((DefaultListableBeanFactory) beanFactory).registerSingleton("webFilterRegistrationBean", bean);
        }
    }
}
