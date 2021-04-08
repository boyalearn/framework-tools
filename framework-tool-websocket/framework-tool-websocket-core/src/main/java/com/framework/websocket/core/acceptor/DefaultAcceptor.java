package com.framework.websocket.core.acceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.framework.websocket.core.message.Message;
import com.framework.websocket.core.protocol.MessageProtocol;
import com.framework.websocket.core.protocol.Protocol;
import com.framework.websocket.core.thread.NamedThreadFactory;
import com.framework.websocket.core.thread.Worker;
import com.framework.websocket.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DefaultAcceptor implements Acceptor {

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            4, 8, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5000),
            new NamedThreadFactory("websocket-reactor-pool-"), new ThreadPoolExecutor.AbortPolicy());


    private Protocol protocol;

    @Override
    public void doAccept(Session session, String stringMessage) {
        Message message;
        try {
            message = JsonUtil.toMessage(stringMessage);
        } catch (JsonProcessingException e) {
            log.error("message error", e);
            return;
        }
        if ("ping".equals(message.getCmd())) {
            try {
                session.getBasicRemote().sendText(JsonUtil.toJson(protocol.getPong()));
            } catch (IOException e) {
                log.error("send message error", e);
                return;
            }
        }
        EXECUTOR.execute(new Worker(null, stringMessage));

    }
}
