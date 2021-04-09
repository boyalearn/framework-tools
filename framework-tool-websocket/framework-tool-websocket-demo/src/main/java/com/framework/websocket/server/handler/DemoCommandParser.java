package com.framework.websocket.server.handler;

import com.framework.websocket.annotation.EndpointPath;
import com.framework.websocket.handler.CommandParser;
import com.framework.websocket.message.Message;
import org.springframework.stereotype.Component;

@EndpointPath("/ws")
@Component
public class DemoCommandParser implements CommandParser {
    @Override
    public String parse(Message message) {
        return message.getCommand();
    }
}
