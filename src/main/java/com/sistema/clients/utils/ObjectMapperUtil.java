package com.sistema.clients.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


public class ObjectMapperUtil {


    public static ObjectMapper getObjectMapper(){
        return Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule())
                .build()
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    public static MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setObjectMapper(getObjectMapper());

        return converter;
    }
}
