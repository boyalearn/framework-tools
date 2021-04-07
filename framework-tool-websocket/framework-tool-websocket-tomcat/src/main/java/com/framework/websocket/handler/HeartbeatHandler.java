package com.framework.websocket.handler;

import com.framework.websocket.context.SessionContext;
import com.framework.websocket.message.Message;
import com.framework.websocket.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class HeartbeatHandler implements Runnable {

    private final static Message<String> PONG_DATA = new Message<>();

    private final static Message<String> PING_DATA = new Message<>();

    private int pingCount = 0;

    static {
        PONG_DATA.setCommand("pong");
        PING_DATA.setCommand("ping");
    }

    private SessionContext sessionContext;

    public HeartbeatHandler(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public void handler(String cmd) throws IOException {
        if (PING_DATA.getCommand().equals(cmd)) {
            sessionContext.sendMessage(JsonUtils.messageToJson(PONG_DATA));
        } else {
            pingCount--;
        }
    }

    @Override
    public void run() {

        log.debug("ping count is {}", pingCount);

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
            sessionContext.sendMessage(JsonUtils.messageToJson(PING_DATA));
            pingCount++;
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
