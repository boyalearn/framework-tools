package com.framework.websocket.spring.handler;

import com.framework.websocket.core.handler.Handler;

public interface CommandHandler extends Handler {

    String getCmd();
}