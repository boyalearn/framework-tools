package com.framework.websocket.handler;

import com.framework.websocket.context.SessionContext;
import com.framework.websocket.message.Message;
import com.framework.websocket.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class HeartbeatHandler implements Runnable {

    private final static Message<String> PONG_DATA = new Message<>();

    private final static Message<String> PING_DATA = new Message<>();

    private int time = 3;

    private AtomicInteger pingCount = new AtomicInteger(0);

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
            pingCount.decrementAndGet();
        }
    }

    @Override
    public void run() {
        log.debug("ping count is {}", pingCount);
        if (pingCount.get() > time) {
            log.debug("server actively close the connection,because client not responding in time");
            close();
        }
        log.debug("one heart beat send...");
        sendPingData();

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
            pingCount.incrementAndGet();
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
