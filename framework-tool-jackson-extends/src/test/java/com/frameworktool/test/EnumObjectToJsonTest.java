package com.frameworktool.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.frameworktool.test.entity.EnumBean;
import com.frameworktool.test.enums.TypeEnum;
import com.frameworktools.jackson.databind.NumEnumDeserializer;
import com.frameworktools.jackson.databind.NumEnumSerializer;
import com.frameworktools.jackson.databind.enums.IEnum;
import org.junit.Before;
import org.junit.Test;

public class EnumObjectToJsonTest {

    private ObjectMapper objectMapper;

    private EnumBean enumBean;


    @Before
    public void before(){
        enumBean=new EnumBean();
        enumBean.setId(1);
        enumBean.setType(TypeEnum.ONE);
    }


    @Test
    public void test_0000001_toJsonTest() throws JsonProcessingException {
        objectMapper=new ObjectMapper();
        SimpleModule simpleModule=new SimpleModule();
        simpleModule.addSerializer(IEnum.class, new NumEnumSerializer());
        simpleModule.addDeserializer(Enum.class,new NumEnumDeserializer(Enum.class));
        objectMapper.registerModule(simpleModule);

        String json = objectMapper.writeValueAsString(enumBean);
        System.out.println(json);

        EnumBean enumBeanCopy = objectMapper.readValue(json, EnumBean.class);
        System.out.println(enumBeanCopy);
    }

    @Test
    public void test_0000002_toJsonTest() throws JsonProcessingException {
        objectMapper=new ObjectMapper();

        String json = objectMapper.writeValueAsString(enumBean);
        System.out.println(json);

        EnumBean enumBeanCopy = objectMapper.readValue(json, EnumBean.class);
        System.out.println(enumBeanCopy);
    }
}
