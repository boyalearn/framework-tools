package com.framework.websocket.core.acceptor;

import com.framework.websocket.core.context.ChannelContext;
import com.framework.websocket.core.context.DefaultChannelContext;
import com.framework.websocket.core.exception.MessageProtocolException;
import com.framework.websocket.core.message.Message;
import com.framework.websocket.core.protocol.Protocol;
import com.framework.websocket.core.reactor.Reactor;
import com.framework.websocket.core.thread.NamedThreadFactory;
import com.framework.websocket.core.thread.Worker;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DefaultAcceptor implements Acceptor {

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            4, 8, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5000),
            new NamedThreadFactory("websocket-reactor-pool-"), new ThreadPoolExecutor.AbortPolicy());

    private Reactor reactor;

    private Protocol<Message> protocol;

    public DefaultAcceptor(Protocol protocol, Reactor reactor) {
        this.protocol = protocol;
        this.reactor = reactor;
    }

    @Override
    public void doAccept(ChannelContext channelContext) {
        EXECUTOR.execute(new Worker(this.reactor, this.protocol, channelContext));
    }
}
