package com.framework.websocket.core.event;

import com.framework.websocket.core.listener.EventListener;

import java.util.List;

/**
 * 事件发布者
 */
public interface EventPublisher {

    void publish(Event e);

    void addListeners(List<EventListener> listeners);
}
