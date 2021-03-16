package com.framework.websocket.session;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSessionManager implements SessionManager {

    private Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void addSession(Session session) {
        sessionMap.put(session.getId(), session);
    }

    @Override
    public void removeSession(Session session) {
        sessionMap.remove(session.getId());
    }
}
