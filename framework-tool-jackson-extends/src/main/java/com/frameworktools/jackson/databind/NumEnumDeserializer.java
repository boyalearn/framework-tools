package com.frameworktools.jackson.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.frameworktool.basetype.IEnum;
import com.frameworktools.jackson.utils.StringUtils;

import java.io.IOException;

public class NumEnumDeserializer extends JsonDeserializer<Enum> implements ContextualDeserializer {

    private Class<Enum> clazz;

    public NumEnumDeserializer(Class<Enum> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public Enum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String index = p.getText();
        if (!StringUtils.isNumber(index)) {
            throw new UnrecognizedPropertyException(p, "The value is mast number", null, clazz, null, null);
        }
        if (!clazz.isEnum()) {
            throw new UnrecognizedPropertyException(p, "Interface IEnum is mast enum", null, clazz, null, null);
        }

        Object[] enums = clazz.getEnumConstants();
        for (Object enumObj : enums) {
            IEnum iEnum = (IEnum) enumObj;
            if (Integer.valueOf(index).equals(iEnum.getIndex())) {
                return (Enum) iEnum;
            }
        }
        return null;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        return new NumEnumDeserializer((Class<Enum>) ctxt.getContextualType().getRawClass());
    }
}
