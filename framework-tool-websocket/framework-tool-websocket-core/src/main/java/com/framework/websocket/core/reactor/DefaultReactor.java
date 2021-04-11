package com.framework.websocket.core.reactor;

import com.framework.websocket.core.context.ChannelContext;
import com.framework.websocket.core.handler.Handler;

import java.util.ArrayList;
import java.util.List;

public class DefaultReactor implements Reactor {

    private List<Handler> handlers = new ArrayList();

    @Override
    public void addHandler(Handler handler) {
        this.handlers.add(handler);
    }

    @Override
    public void addHandlers(List<Handler> handlers) {
        this.handlers.addAll(handlers);
    }

    @Override
    public void dispatch(String message, ChannelContext context) {
        for (Handler handler : this.handlers) {
            handler.handle(context, message);
        }
    }

}
