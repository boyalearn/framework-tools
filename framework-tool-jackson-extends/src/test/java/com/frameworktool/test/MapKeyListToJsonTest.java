package com.frameworktool.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.frameworktool.test.entity.ListMapBean;
import com.frameworktools.jackson.databind.NumEnumDeserializer;
import com.frameworktools.jackson.databind.NumEnumSerializer;
import com.frameworktools.jackson.databind.enums.IEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapKeyListToJsonTest {

    private ObjectMapper objectMapper;

    private Map mapObject;


    @Before
    public void before() {
        objectMapper = new ObjectMapper();
        List<String> listKey = new ArrayList();
        listKey.add("1");
        listKey.add("2");
        listKey.add("3");

        List<String> listValue = new ArrayList();
        listValue.add("5");
        listValue.add("6");
        listValue.add("7");

        mapObject = new HashMap();
        mapObject.put(listKey, listValue);

    }

    @Test
    public void test_0000001_toJsonTest() throws JsonProcessingException {

        ListMapBean listMapBean = new ListMapBean();
        listMapBean.setMap(mapObject);

        String json = objectMapper.writeValueAsString(listMapBean);
        System.out.println(json);

        ListMapBean listMapBeanCopy = objectMapper.readValue(json, ListMapBean.class);
        System.out.println(listMapBeanCopy);
    }


    @Test
    public void test_0000002_toJsonTest() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(IEnum.class, new NumEnumSerializer());
        simpleModule.addDeserializer(Enum.class, new NumEnumDeserializer(Enum.class));
        objectMapper.registerModule(simpleModule);
    }
}
