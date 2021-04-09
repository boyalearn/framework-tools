package com.framework.websocket.core.thread;

import com.framework.websocket.core.handler.Handler;

import java.util.Map;

public class Worker implements Runnable {

    private Map<String, Handler> dispatch;

    private String context;

    public Worker(Map<String, Handler> dispatch, String context) {
        this.dispatch = dispatch;
        this.context = context;
    }

    @Override
    public void run() {
        this.dispatch.get(context);
    }
}
