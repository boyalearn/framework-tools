package com.framework.websocket.core.server;

import com.framework.websocket.core.context.DefaultChannelContext;
import com.framework.websocket.core.context.ServerContext;
import com.framework.websocket.core.event.CloseEvent;
import com.framework.websocket.core.event.ConnectionEvent;
import com.framework.websocket.core.event.ErrorEvent;
import com.framework.websocket.core.event.EventPublisher;
import com.framework.websocket.core.event.MessageEvent;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@Slf4j
public class WebSocketServerEndpoint {

    private EventPublisher publisher;

    public WebSocketServerEndpoint() {
        this.publisher = ServerContext.getEventPublisher();
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        log.debug("session id {} connect...", session.getId());
        publisher.publish(new ConnectionEvent(new DefaultChannelContext(session, config)));
    }


    @OnMessage
    public void onMessage(Session session, String message) {
        log.debug("session id {} on message...", session.getId());
        publisher.publish(new MessageEvent(new DefaultChannelContext(session, message)));
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.debug("session id {} on error...", session.getId(), error);
        publisher.publish(new ErrorEvent(new DefaultChannelContext(session, error)));

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.debug("session id {} close...", session.getId());
        publisher.publish(new CloseEvent(new DefaultChannelContext(session, closeReason)));
    }
}
