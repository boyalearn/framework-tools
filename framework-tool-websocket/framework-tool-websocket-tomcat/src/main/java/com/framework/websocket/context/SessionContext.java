package com.framework.websocket.context;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;

@Slf4j
public class SessionContext {


    private Session session;

    public SessionContext(Session session) {
        this.session = session;
    }

    public synchronized void sendMessage(String message) throws IOException {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("send error");
            throw e;
        }
    }

    public void close() throws IOException {

        try {
            log.error("close");
            this.session.close();
        } catch (Exception e) {
            log.debug("close exception", e);
            throw e;
        }
    }
}
