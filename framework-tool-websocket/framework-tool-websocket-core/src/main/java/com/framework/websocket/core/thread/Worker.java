package com.framework.websocket.core.thread;

import com.framework.websocket.core.reactor.Reactor;

public class Worker implements Runnable {

    private Reactor reactor;

    private String context;

    public Worker(Reactor reactor, String context) {
        this.reactor = reactor;
        this.context = context;
    }

    @Override
    public void run() {
        reactor.dispatch(context);
    }
}
