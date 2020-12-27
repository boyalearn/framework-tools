package com.framework.tool.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static final String objectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
