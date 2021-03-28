package com.framework.websocket.handler;

import javax.websocket.Session;

public interface ErrorHandler {
    void handlingException(Session session, Throwable error);
}
