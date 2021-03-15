package com.framework.tool.websocket;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;
import javax.websocket.Extension;
import javax.websocket.server.ServerEndpointConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebSocketEndpointConfigBean implements ServerEndpointConfig {

    private Endpoint endpoint;

    private String path;

    private List<String> subProtocols = new ArrayList<>(0);

    private List<Extension> extensions = new ArrayList<>(0);

    private List<Class<? extends Encoder>> encoders = new ArrayList<>(0);

    private List<Class<? extends Decoder>> decoders = new ArrayList<>(0);

    private final Map<String, Object> userProperties = new HashMap<>(4);

    private ServerEndpointConfig.Configurator configurator;


    public WebSocketEndpointConfigBean(String path, Endpoint endpoint) {
        this.endpoint = endpoint;
        this.path = path;
    }

    public void setConfigurator(ServerEndpointConfig.Configurator configurator) {
        this.configurator = configurator;
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

}
