package com.recordpoint.connectors.sdk.json;

public class JsonMapperException extends Exception {

    public JsonMapperException(Exception e, Class type) {
        super(String.format("There was an error to parse a %s type", type.getCanonicalName()), e);
    }

}
