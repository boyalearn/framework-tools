package com.framework.tool.websocket.handler;

import com.framework.tool.websocket.context.SessionContext;
import com.framework.tool.websocket.message.Message;
import com.framework.tool.websocket.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class HeartbeatHandler implements Runnable {

    private final static Message<String> PONG_DATA = new Message<>();

    private final static Message<String> PING_DATA = new Message<>();

    static {
        PONG_DATA.setCommand("pong");
        PING_DATA.setCommand("ping");
    }

    private SessionContext sessionContext;

    public HeartbeatHandler(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public void handler() throws IOException {
        sessionContext.sendMessage(JsonUtils.messageToJson(PONG_DATA));
    }

    @Override
    public void run() {
        log.debug("one heart beat send...");
        int time = 3;
        while (time >= 0 && !sendPingData()) {
            time--;
            sleep(3 * 1000);
        }
        if (time < 0) {
            close();
        }
    }

    private void close() {
        try {
            sessionContext.close();
        } catch (IOException e) {
            log.debug("server actively close the connection");
        }
    }

    private boolean sendPingData() {
        try {
            this.sessionContext.sendMessage(JsonUtils.messageToJson(PING_DATA));
            return true;
        } catch (IOException e) {
            log.debug("send ping error.", e);
            return false;
        }
    }

    private void sleep(long timeOut) {
        try {
            Thread.sleep(timeOut);
        } catch (InterruptedException e) {
            log.debug("interrupted exception.", e);
        }
    }
}
