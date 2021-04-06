package com.framework.tool.websocket.context;

import javax.websocket.Session;
import java.io.IOException;

public class SessionContext {


    private Session session;

    public SessionContext(Session session) {
        this.session = session;
    }

    public synchronized void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public void close() throws IOException {
    }


    public Session getSession() {
        return session;
    }
}
