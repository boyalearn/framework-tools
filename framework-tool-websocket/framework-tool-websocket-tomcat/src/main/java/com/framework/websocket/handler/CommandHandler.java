package com.framework.websocket.handler;

import javax.websocket.Session;

public interface CommandHandler {

    void handle(Session session, String message) throws Exception;
}
