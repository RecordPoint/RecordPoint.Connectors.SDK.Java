package com.recordpoint.connectors.sdk.http;

import com.google.common.io.ByteStreams;
import com.recordpoint.connectors.sdk.json.JsonMapper;
import com.recordpoint.connectors.sdk.json.JsonMapperException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface representing content that can be streamed as an input stream or written to an output stream.
 * Designed to abstract content handling for streaming operations.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public interface StreamingContent {

    /**
     * Retrieves the content as an {@link InputStream}.
     *
     * @return an {@link InputStream} representing the content.
     * @throws IOException if an I/O error occurs while retrieving the input stream.
     */
    InputStream getInputStream() throws IOException;

    /**
     * Retrieves the underlying content object.
     *
     * @return the content object.
     */
    Object getContent();

    /**
     * Converts the content to its JSON string representation using the provided {@link JsonMapper}.
     *
     * @param mapper the {@link JsonMapper} used to convert the content to JSON.
     * @return a JSON string representation of the content.
     * @throws JsonMapperException if an error occurs during the conversion process.
     */
    default String getJsonRepresentation(JsonMapper mapper) throws JsonMapperException {
        return mapper.parseAsString(getContent());
    }

    /**
     * Writes the content to the specified {@link OutputStream}.
     *
     * <p>The method copies the content from the {@link InputStream} returned by {@link #getInputStream()}
     * to the provided output stream. The output stream is flushed after the copy operation.</p>
     *
     * @param outputStream the {@link OutputStream} to which the content will be written.
     * @throws IOException if an I/O error occurs during the write operation.
     */
    default void writeTo(OutputStream outputStream) throws IOException {
        try (InputStream inputStream = getInputStream()) {
            ByteStreams.copy(inputStream, outputStream);
        }
        outputStream.flush();
    }
}
