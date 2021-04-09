package com.framework.websocket.core.context;

import com.framework.websocket.core.message.Message;
import com.framework.websocket.core.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.io.IOException;

@Getter
@Setter
@Slf4j
public class DefaultChannelContext implements ChannelContext {

    private Session session;

    private String message;

    private Throwable exception;

    private CloseReason closeReason;

    private EndpointConfig config;

    public DefaultChannelContext(Session session, Throwable exception) {
        this.session = session;
        this.exception = exception;
    }

    public DefaultChannelContext(Session session, EndpointConfig config) {
        this.session = session;
        this.config = config;
    }

    public DefaultChannelContext(Session session, String message) {
        this.session = session;
        this.message = message;
    }

    public DefaultChannelContext(Session session, CloseReason closeReason) {
        this.session = session;
        this.closeReason = closeReason;
    }

    @Override
    public void sendMessage(Message message) {
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(JsonUtil.toJson(message));
            } catch (IOException e) {
                log.error("send message exception");
            }
        }
    }

    @Override
    public void sendMessage(String message) {
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("send message exception");
            }
        }
    }
}
