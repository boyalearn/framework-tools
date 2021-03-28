package com.framework.websocket.handler;

import com.framework.websocket.message.Message;

public interface CommandParser {
    String parse(Message message);
}
