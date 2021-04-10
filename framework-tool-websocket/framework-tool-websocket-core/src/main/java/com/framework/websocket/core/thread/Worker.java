package com.framework.websocket.core.thread;

import com.framework.websocket.core.context.ChannelContext;
import com.framework.websocket.core.reactor.Reactor;

public class Worker implements Runnable {

    private Reactor reactor;

    private String cmd;

    private ChannelContext context;

    public Worker(Reactor reactor, String cmd, ChannelContext context) {
        this.reactor = reactor;
        this.cmd = cmd;
        this.context = context;
    }

    @Override
    public void run() {
        reactor.dispatch(this.cmd, this.context);
    }
}
