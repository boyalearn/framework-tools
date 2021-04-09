package com.framework.websocket.core.thread;

import com.framework.websocket.core.context.ChannelContext;
import com.framework.websocket.core.message.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartbeatWorker implements Runnable {


    private final static Message PONG_DATA = new Message();

    private final static Message PING_DATA = new Message();

    static {
        PONG_DATA.setCmd("pong");
        PING_DATA.setCmd("ping");
    }

    private ChannelContext channelContext;

    public HeartbeatWorker(ChannelContext channelContext) {
        this.channelContext = channelContext;
    }


    @Override
    public void run() {
        channelContext.sendMessage(PING_DATA);
    }
}
