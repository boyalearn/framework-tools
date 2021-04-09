package com.framework.websocket.core.event;

import com.framework.websocket.core.context.ChannelContext;

public class MessageEvent extends AbstractEvent{
    public MessageEvent(ChannelContext channelContext) {
        super(channelContext);
    }
}
