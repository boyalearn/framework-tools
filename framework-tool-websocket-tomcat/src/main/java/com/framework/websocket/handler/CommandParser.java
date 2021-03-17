package com.framework.websocket.handler;

public interface CommandParser {
    Command parse(Object message);
}
