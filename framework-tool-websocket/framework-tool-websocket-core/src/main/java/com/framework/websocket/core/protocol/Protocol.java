package com.framework.websocket.core.protocol;

import com.framework.websocket.core.exception.MessageProtocolException;
import com.framework.websocket.core.message.Message;

public interface Protocol {

    default Message getPing() {
        Message message = new Message();
        message.setCmd("ping");
        return message;
    }

    default Message getPong() {
        Message message = new Message();
        message.setCmd("pong");
        return message;
    }

    Message parseMessage(String messageContext) throws MessageProtocolException;

    String getCmd(String message);
}
