package com.framework.websocket.session;

import javax.websocket.Session;

public interface SessionManager {

    void addSession(Session session);

    void removeSession(Session session);
}
