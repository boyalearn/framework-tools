package com.frameworktools.jackson.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.frameworktools.jackson.databind.enums.IEnum;

import java.io.IOException;

public class NumEnumSerializer extends StdScalarSerializer<IEnum> implements ContextualSerializer {

    private Class<IEnum> iEnumClass;

    protected NumEnumSerializer(Class<IEnum> t) {
        super(t);
        this.iEnumClass = t;
    }

    public NumEnumSerializer(){
        super(IEnum.class);
    }

    @Override
    public void serialize(IEnum value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(value.getIndex());
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        return new NumEnumSerializer((Class<IEnum>) property.getType().getRawClass());
    }
}
