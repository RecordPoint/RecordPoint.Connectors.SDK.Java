package com.recordpoint.connectors.sdk.json.jackson;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeParseException;

public class CustomInstantDeserializer extends FromStringDeserializer<Instant> {

    protected CustomInstantDeserializer() {
        super(Instant.class);
    }

    @Override
    protected Instant _deserialize(String value, DeserializationContext context) throws IOException {
        try {
            return Instant.parse(value);
        } catch (DateTimeParseException e) {
            // Handle invalid date and return a default value (e.g., Instant.EPOCH)
            return Instant.EPOCH;
        }
    }

}
