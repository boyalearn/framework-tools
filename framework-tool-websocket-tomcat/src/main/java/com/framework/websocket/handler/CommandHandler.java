package com.framework.websocket.handler;

import com.framework.websocket.annotation.EndpointPath;

import javax.websocket.Session;

@EndpointPath("/ws")
public interface CommandHandler {

    void handle(Session session, String message);
}
