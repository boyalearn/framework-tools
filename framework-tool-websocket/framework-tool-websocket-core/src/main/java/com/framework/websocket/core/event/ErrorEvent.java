package com.framework.websocket.core.event;

import com.framework.websocket.core.context.ChannelContext;

public class ErrorEvent extends AbstractEvent {
    public ErrorEvent(ChannelContext channelContext) {
        super(channelContext);
    }
}
