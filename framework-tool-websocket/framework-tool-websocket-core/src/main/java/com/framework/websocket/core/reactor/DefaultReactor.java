package com.framework.websocket.core.reactor;

import com.framework.websocket.core.context.ChannelContext;
import com.framework.websocket.core.context.DefaultChannelContext;
import com.framework.websocket.core.handler.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultReactor implements Reactor {

    private List<Handler> handlers = new ArrayList();

    private volatile Map<String, Handler> dispatcherCenter;

    @Override
    public void addHandler(Handler handler) {
        this.handlers.add(handler);
    }

    @Override
    public void addHandlers(List<Handler> handlers) {
        this.handlers.addAll(handlers);
    }

    @Override
    public void dispatch(String cmd, ChannelContext context) {
        if (null == this.dispatcherCenter) {
            Map<String, Handler> map = new HashMap<>();
            for (Handler handler : this.handlers) {
                map.put(handler.getCmd(), handler);
            }
            this.dispatcherCenter = map;
        }
        Handler handler = this.dispatcherCenter.get(cmd);
        if (null != handler) {
            handler.handle(context, ((DefaultChannelContext) context).getMessage());
        }
    }

}
