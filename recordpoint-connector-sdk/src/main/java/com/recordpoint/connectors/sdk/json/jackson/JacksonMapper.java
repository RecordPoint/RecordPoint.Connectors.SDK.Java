package com.recordpoint.connectors.sdk.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.recordpoint.connectors.sdk.json.JsonMapper;
import com.recordpoint.connectors.sdk.json.JsonMapperException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public final class JacksonMapper implements JsonMapper {

    public static ObjectMapper getDefaultInstance() {
        return MapperHolder.INSTANCE;
    }

    @Override
    public <T> T parseAndClose(InputStream in, Charset charset, Class<T> destinationClass) throws JsonMapperException {
        try {
            return getDefaultInstance().readValue(in, destinationClass);
        } catch (Exception e) {
            throw new JsonMapperException(e, destinationClass);
        }
    }

    @Override
    public <T> List<T> parseAsList(InputStream in, Charset charset, Class<T> type) throws JsonMapperException {
        try {
            return getDefaultInstance().readValue(in, getDefaultInstance().getTypeFactory().constructCollectionType(List.class, type));
        } catch (Exception e) {
            throw new JsonMapperException(e, type);
        }
    }

    @Override
    public String parseAsString(Object content) throws JsonMapperException {
        try {
            return getDefaultInstance().writeValueAsString(content);
        } catch (JsonProcessingException e) {
            throw new JsonMapperException(e, content.getClass());
        }
    }

    @Override
    public Map<String, String> parseAsMap(InputStream in) throws JsonMapperException {
        try {
            return getDefaultInstance().readValue(in,
                    getDefaultInstance().getTypeFactory().constructMapType(Map.class, String.class, String.class));
        } catch (IOException e) {
            throw new JsonMapperException(e, Map.class);
        }
    }

    static class MapperHolder {
        static final ObjectMapper INSTANCE = new ObjectMapper();

        static {
            JavaTimeModule timeModule = new JavaTimeModule();
            timeModule.addDeserializer(Instant.class, new CustomInstantDeserializer());
            INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            INSTANCE.registerModule(timeModule);
            INSTANCE.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            INSTANCE.setSerializationInclusion(NON_NULL);
        }
    }

}
