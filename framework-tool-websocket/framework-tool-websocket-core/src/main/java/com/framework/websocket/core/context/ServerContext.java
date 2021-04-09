package com.framework.websocket.core.context;

import com.framework.websocket.core.event.EventPublisher;

public class ServerContext {
    private static EventPublisher PUBLISHER;

    public static EventPublisher getEventPublisher() {
        return PUBLISHER;
    }

    public static void setEventPublisher(EventPublisher eventPublisher) {
        PUBLISHER = eventPublisher;
    }
}
