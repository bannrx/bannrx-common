package com.bannrx.common.persistence.converters;

import com.bannrx.common.dtos.verification.VerificationData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Converter
public class VerificationDataConverter implements AttributeConverter<VerificationData, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(VerificationData verificationData) {
        if (Objects.nonNull(verificationData)){
            try {
                return mapper.writeValueAsString(verificationData);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Error while converting verification data");
            }
        }
        return null;
    }

    @Override
    public VerificationData convertToEntityAttribute(String s) {
        if (StringUtils.isNotBlank(s)){
            try {
                return mapper.readValue(s, VerificationData.class);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Error while parsing verification data while retrieving from database. Json: " + s);
            }
        }
        return null;
    }
}
