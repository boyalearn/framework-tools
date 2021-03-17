package com.framework.websocket.test.handler;

import com.framework.websocket.annotation.CmdName;
import com.framework.websocket.annotation.EndpointPath;
import com.framework.websocket.handler.Command;
import com.framework.websocket.handler.CommandHandler;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

@EndpointPath("ws")
@Component
@CmdName(Command.RPC_CALL)
public class DemoCommandHandler implements CommandHandler {
    @Override
    public void handle(Session session, String message) {
        System.out.println(session);
    }
}
