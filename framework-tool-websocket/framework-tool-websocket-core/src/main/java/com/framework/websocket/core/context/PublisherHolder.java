package com.framework.websocket.core.context;

import com.framework.websocket.core.config.ServerConfig;
import com.framework.websocket.core.event.EventPublisher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PublisherHolder {


    private static ServerConfig SERVER_CONFIG;

    private static final Map<Class<?>, EventPublisher> PUBLISHER_CENTER = new ConcurrentHashMap<>();

    public static EventPublisher findEventPublisher(Class<?> clazz) {
        if (null == SERVER_CONFIG) {
            return null;
        }
        EventPublisher publisher = PUBLISHER_CENTER.get(clazz);
        if (null == publisher) {
            synchronized (PUBLISHER_CENTER) {
                if (null == PUBLISHER_CENTER.get(clazz)) {
                    EventPublisher eventPublisher = SERVER_CONFIG.buildPublisherForEndpoint(clazz);
                    PUBLISHER_CENTER.put(clazz, eventPublisher);
                    return eventPublisher;
                }
                return PUBLISHER_CENTER.get(clazz);
            }
        }
        return publisher;
    }

    public static void setServerConfig(ServerConfig serverConfig) {
        SERVER_CONFIG = serverConfig;
    }
}
