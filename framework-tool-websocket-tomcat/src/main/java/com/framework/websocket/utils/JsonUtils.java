package com.framework.websocket.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.websocket.message.Message;

public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Message jsonToMessage(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Message.class);
    }
}
