package com.framework.websocket.core.handler;

import com.framework.websocket.core.context.ChannelContext;

public interface Handler {

    String getCmd();

    void handle(ChannelContext channelContext, String body);
}
