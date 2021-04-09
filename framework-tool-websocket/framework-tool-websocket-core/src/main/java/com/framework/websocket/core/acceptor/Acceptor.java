package com.framework.websocket.core.acceptor;

import com.framework.websocket.core.context.ChannelContext;

public interface Acceptor {

    void doAccept(ChannelContext channelContext);
}
