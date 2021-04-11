package com.framework.websocket.core.endpoint;

import com.framework.websocket.core.config.ServerConfig;
import com.framework.websocket.core.context.DefaultChannelContext;
import com.framework.websocket.core.context.PublishHolder;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class AbstractWebSocketServerEndpoint implements WebSocketServerEndpoint {

    private AtomicInteger sendId = new AtomicInteger(1);

    protected static Map<Class<?>, EventPublisher> publisherCenter = new ConcurrentHashMap<>();

    private EventPublisher publisher;

    private Session session;

    public AbstractWebSocketServerEndpoint() {
        this.publisher = publisherCenter.get(this.getClass());

        //启动默认配置
        if (null == this.publisher && null == PublishHolder.getEventPublisher()) {
            log.debug("start default config");
            ServerConfig serverConfig = new ServerConfig.Builder().build();
            serverConfig.parserConfig();
            PublishHolder.setEventPublisher(serverConfig.getPublisher());
            this.publisher = PublishHolder.getEventPublisher();
        }
        if (null == this.publisher) {
            this.publisher = PublishHolder.getEventPublisher();
        }
    }

    @Override
    public AtomicInteger sendId() {
        return sendId;
    }

    public Session getSession() {
        return session;
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        log.debug("session id {} connect...", session.getId());
        this.session = session;
        publisher.publish(new ConnectionEvent(new DefaultChannelContext(this, config)));
    }


    @OnMessage
    public void onMessage(Session session, String message) {
        log.debug("session id {} on message...", session.getId());
        publisher.publish(new MessageEvent(new DefaultChannelContext(this, message)));
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.debug("session id {} on error...", session.getId(), error);
        publisher.publish(new ErrorEvent(new DefaultChannelContext(this, error)));

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.debug("session id {} close...", session.getId());
        publisher.publish(new CloseEvent(new DefaultChannelContext(this, closeReason)));
    }
}
