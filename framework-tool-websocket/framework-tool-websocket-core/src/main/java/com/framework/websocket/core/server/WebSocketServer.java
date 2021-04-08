package com.framework.websocket.core.server;

import com.framework.websocket.core.acceptor.Acceptor;
import com.framework.websocket.core.monitor.ConnectionMonitor;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@Slf4j
public class WebSocketServer {

    private static Acceptor acceptor;

    private ConnectionMonitor connectionMonitor;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        log.debug("session id {} connect...", session.getId());
        connectionMonitor.monitor(session);
    }


    @OnMessage
    public void onMessage(Session session, String message) throws Exception {
        acceptor.doAccept(session, message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.debug("session id {} on error...", session.getId(), error);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.debug("session id {} close...", session.getId());
    }
}
