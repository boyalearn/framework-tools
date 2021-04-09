package com.framework.websocket.core.acceptor;

import com.framework.websocket.core.context.ChannelContext;
import com.framework.websocket.core.context.DefaultChannelContext;
import com.framework.websocket.core.exception.MessageProtocolException;
import com.framework.websocket.core.handler.Handler;
import com.framework.websocket.core.message.Message;
import com.framework.websocket.core.protocol.Protocol;
import com.framework.websocket.core.thread.NamedThreadFactory;
import com.framework.websocket.core.thread.Worker;
import com.framework.websocket.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DefaultAcceptor implements Acceptor {

    private static final String PING = "ping";

    private static final String PONG = "pong";

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            4, 8, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5000),
            new NamedThreadFactory("websocket-reactor-pool-"), new ThreadPoolExecutor.AbortPolicy());

    private Map<String, Handler> dispatch;

    private Protocol protocol;

    public DefaultAcceptor(Protocol protocol, Map<String, Handler> dispatch) {
        this.protocol = protocol;
        this.dispatch = dispatch;
    }

    @Override
    public void doAccept(ChannelContext channelContext) {
        Message message;
        try {
            message = protocol.parseMessage(((DefaultChannelContext) channelContext).getMessage());
        } catch (MessageProtocolException e) {
            log.error("message error", e);
            return;
        }
        if (PING.equals(message.getCmd())) {
            try {
                channelContext.sendMessage(JsonUtil.toJson(protocol.getPong()));
            } catch (IOException e) {
                log.error("send message error", e);
                return;
            }
            return;
        }

        if (PONG.equals(message.getCmd())) {
            log.info("accept pong");
        }
        EXECUTOR.execute(new Worker(dispatch, ((DefaultChannelContext) channelContext).getMessage()));
    }
}
