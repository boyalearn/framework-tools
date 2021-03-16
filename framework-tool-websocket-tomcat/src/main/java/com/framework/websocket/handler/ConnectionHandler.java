package com.framework.websocket.handler;

import com.framework.websocket.context.ChannelContext;
import com.framework.websocket.exception.ConnectionException;

import javax.websocket.CloseReason;
import javax.websocket.Session;

public interface ConnectionHandler {
    void onOpen(ChannelContext context) throws ConnectionException;

    void onClose(Session session, CloseReason closeReason);
}
