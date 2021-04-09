package com.framework.websocket.core.event;

import com.framework.websocket.core.context.ChannelContext;

public abstract class AbstractEvent implements Event {

    private ChannelContext channelContext;


    public AbstractEvent(ChannelContext channelContext) {
        this.channelContext = channelContext;
    }

    @Override
    public ChannelContext getChannelContext() {
        return channelContext;
    }
}
