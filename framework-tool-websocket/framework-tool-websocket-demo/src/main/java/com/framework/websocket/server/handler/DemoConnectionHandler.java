package com.framework.websocket.server.handler;

import com.framework.websocket.annotation.EndpointPath;
import com.framework.websocket.context.ChannelContext;
import com.framework.websocket.context.SessionContext;
import com.framework.websocket.exception.ConnectionException;
import com.framework.websocket.handler.ConnectionHandler;
import org.springframework.stereotype.Component;

import javax.websocket.CloseReason;

@EndpointPath("/ws")
@Component
public class DemoConnectionHandler implements ConnectionHandler {
    @Override
    public void onOpen(ChannelContext context) throws ConnectionException {
    }

    @Override
    public void onClose(SessionContext session, CloseReason closeReason) {

    }
}
