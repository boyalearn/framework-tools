package com.framework.websocket.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.websocket.core.message.Message;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Message toMessage(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Message.class);
    }

    public static String toJson(Message message) throws JsonProcessingException {
        return objectMapper.writeValueAsString(message);
    }
}
