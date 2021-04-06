package com.framework.tool.websocket.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.tool.websocket.message.Message;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Message jsonToMessage(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Message.class);
    }

    public static String messageToJson(Message message) throws JsonProcessingException {
        return objectMapper.writeValueAsString(message);
    }
}
