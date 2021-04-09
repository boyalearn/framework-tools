package com.framework.websocket.core.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.framework.websocket.core.exception.MessageProtocolException;
import com.framework.websocket.core.message.Message;
import com.framework.websocket.core.util.JsonUtil;

public class MessageProtocol implements Protocol {
    @Override
    public Message parseMessage(String messageContext) throws MessageProtocolException {
        try {
            return JsonUtil.toMessage(messageContext);
        } catch (JsonProcessingException e) {
            throw new MessageProtocolException();
        }
    }

    @Override
    public String getCmd(String message) {
        try {
            return JsonUtil.toMessage(message).getCmd();
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
