package com.http.demo.client.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static Map<String, String> jsonToMap(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Map.class);
    }
}
