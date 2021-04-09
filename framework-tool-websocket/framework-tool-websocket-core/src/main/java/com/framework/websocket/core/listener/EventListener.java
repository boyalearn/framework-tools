package com.framework.websocket.core.listener;

public interface EventListener<Event> {
    void onEvent(Event event);
}
