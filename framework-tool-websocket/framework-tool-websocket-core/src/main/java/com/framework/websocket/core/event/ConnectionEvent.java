package com.framework.websocket.core.event;

import com.framework.websocket.core.context.ChannelContext;

public class ConnectionEvent extends AbstractEvent{

    public ConnectionEvent(ChannelContext channelContext) {
        super(channelContext);
    }
}
