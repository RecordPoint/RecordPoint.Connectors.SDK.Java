package com.recordpoint.connectors.sdk.json;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public interface JsonMapper {

    <T> T parseAndClose(InputStream in, Charset charset, Class<T> destinationClass) throws JsonMapperException;

    <T> List<T> parseAsList(InputStream in, Charset charset, Class<T> type) throws JsonMapperException;

    String parseAsString(Object content) throws JsonMapperException;

    Map<String, String> parseAsMap(InputStream in) throws JsonMapperException;

}
