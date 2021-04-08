package com.framework.websocket.core.acceptor;

import javax.websocket.Session;

public interface Acceptor {

    void doAccept(Session session, String message);
}
