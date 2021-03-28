package com.framework.tool.websocket;

import com.framework.tool.websocket.protocol.Command;
import com.framework.tool.websocket.protocol.Message;

public interface MessageDispatcher {
    Command getCommand(Message message);
}
