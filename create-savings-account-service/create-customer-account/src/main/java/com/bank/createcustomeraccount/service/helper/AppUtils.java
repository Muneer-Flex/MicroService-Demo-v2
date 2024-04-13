package com.bank.createcustomeraccount.service.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

@Component
public class AppUtils {
    static final ObjectMapper objectMapper;
    static final ObjectMapper listBasedObjectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        listBasedObjectMapper = JsonMapper.builder()
                .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();
    }

    public static<T> String convertToJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T deserialize(String payload, Class<T> valueType) {
        try {
            return objectMapper.readValue(payload, valueType);
        }catch(JsonProcessingException jsonProcessingException){
            throw new RuntimeException(jsonProcessingException);
        }
    }

    public static <T> T deserialize(String payload, JavaType valueType) {
        try {
            return objectMapper.readValue(payload, valueType);
        }catch(JsonProcessingException jsonProcessingException){
            throw new RuntimeException(jsonProcessingException);
        }
    }

    public static <T> T deserialize(String payload, TypeReference<T> valueType) {
        try {
            return objectMapper.readValue(payload, valueType);
        }catch(JsonProcessingException jsonProcessingException){
            throw new RuntimeException(jsonProcessingException);
        }
    }

    public static <T> T[] deserializeList(String payload, Class<T[]> valueType) {
        try {
            return listBasedObjectMapper.readValue(payload, valueType);
        } catch(JsonProcessingException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
