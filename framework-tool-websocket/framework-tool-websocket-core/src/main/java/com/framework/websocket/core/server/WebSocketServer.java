package com.framework.websocket.core.server;

import com.framework.websocket.core.acceptor.DefaultAcceptor;
import com.framework.websocket.core.context.ServerContext;
import com.framework.websocket.core.event.DefaultEventPublisher;
import com.framework.websocket.core.event.EventPublisher;
import com.framework.websocket.core.listener.EventListener;
import com.framework.websocket.core.listener.HeartbeatListener;
import com.framework.websocket.core.listener.MessageReceiveListener;
import com.framework.websocket.core.protocol.MessageProtocol;
import com.framework.websocket.core.protocol.Protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebSocketServer {

    private EventPublisher publisher = new DefaultEventPublisher();

    private Protocol protocol = new MessageProtocol();

    private List<EventListener> listeners;


    public void start() {
        this.listeners = new ArrayList<>();
        this.listeners.add(new HeartbeatListener());
        this.listeners.add(new MessageReceiveListener(new DefaultAcceptor(this.protocol, new HashMap<>())));
        publisher.setListeners(this.listeners);
        ServerContext.setEventPublisher(publisher);
    }

}
