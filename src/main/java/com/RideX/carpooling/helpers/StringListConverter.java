package com.RideX.carpooling.helpers;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        logger.info("StringListConverter :: convertToDatabaseColumn(List<String> list :[{}])",list);
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not convert list to JSON string.", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String json) {
        logger.info("StringListConverter :: convertToEntityAttribute(json :[{}])",json);
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not convert JSON string to list.", e);
        }
    }
}

