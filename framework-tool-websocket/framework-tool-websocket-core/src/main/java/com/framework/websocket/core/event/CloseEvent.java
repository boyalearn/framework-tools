package com.framework.websocket.core.event;

import com.framework.websocket.core.context.ChannelContext;

public class CloseEvent extends AbstractEvent{


    public CloseEvent(ChannelContext channelContext) {
        super(channelContext);
    }
}
