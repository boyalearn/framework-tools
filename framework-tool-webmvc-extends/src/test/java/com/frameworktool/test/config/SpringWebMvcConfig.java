package com.frameworktool.test.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.frameworktool.basetype.IEnum;
import com.frameworktool.webmvc.converter.NumberEnumConverterFactory;
import com.frameworktools.jackson.databind.NumEnumDeserializer;
import com.frameworktools.jackson.databind.NumEnumSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class SpringWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.removeConvertible(String.class, Enum.class);
        registry.removeConvertible(Enum.class, String.class);
        registry.addConverterFactory(new NumberEnumConverterFactory());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            } else if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter messageConverter = (MappingJackson2HttpMessageConverter) converter;
                messageConverter.setObjectMapper(objectMapper());
            }
        }
    }

    private ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(IEnum.class, new NumEnumSerializer());
        simpleModule.addDeserializer(Enum.class, new NumEnumDeserializer(Enum.class));
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}
