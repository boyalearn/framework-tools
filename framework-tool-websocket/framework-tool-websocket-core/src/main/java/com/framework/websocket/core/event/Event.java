package com.framework.websocket.core.event;

import com.framework.websocket.core.context.ChannelContext;

public interface Event {

    ChannelContext getChannelContext();
}
