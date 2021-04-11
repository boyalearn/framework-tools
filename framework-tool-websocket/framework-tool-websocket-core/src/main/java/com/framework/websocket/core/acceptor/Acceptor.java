package com.framework.websocket.core.acceptor;

import com.framework.websocket.core.context.ChannelContext;
import com.framework.websocket.core.protocol.Protocol;
import com.framework.websocket.core.reactor.Reactor;

public interface Acceptor {

    void setReactor(Reactor reactor);

    void setProtocol(Protocol protocol);

    void doAccept(ChannelContext channelContext);
}
