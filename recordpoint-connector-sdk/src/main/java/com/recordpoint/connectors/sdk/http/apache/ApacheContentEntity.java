package com.recordpoint.connectors.sdk.http.apache;

import org.apache.hc.core5.http.io.entity.AbstractHttpEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A custom {@link AbstractHttpEntity} implementation that represents a streaming content entity.
 * This class is designed to work with Apache HttpClient v5, providing a JSON content entity.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
final class ApacheContentEntity extends AbstractHttpEntity {

    /**
     * Content length or less than zero if not known.
     */
    private final long contentLength;

    /**
     * Streaming content in JSON format.
     */
    private final String jsonContent;

    /**
     * Constructs an {@link ApacheContentEntity} with the provided JSON content, content type, and encoding.
     *
     * @param jsonContent     the JSON content to be streamed.
     * @param contentType     the content type of the entity (e.g., "application/json").
     * @param contentEncoding the content encoding (e.g., "UTF-8").
     */
    ApacheContentEntity(
            String jsonContent,
            String contentType,
            String contentEncoding) {
        super(contentType, contentEncoding, false);
        this.contentLength = jsonContent.length();
        this.jsonContent = jsonContent;
    }

    /**
     * Retrieves the content of the entity as an {@link InputStream}.
     *
     * @return an {@link InputStream} representing the content.
     * @throws IOException if an error occurs while retrieving the content stream.
     */
    @Override
    public InputStream getContent() throws IOException {
        return new ByteArrayInputStream(jsonContent.getBytes());
    }

    /**
     * Retrieves the length of the content.
     *
     * @return the length of the content.
     */
    @Override
    public long getContentLength() {
        return jsonContent.length();
    }

    /**
     * Indicates whether the content can be repeated.
     *
     * @return {@code false}, as the content is not repeatable.
     */
    @Override
    public boolean isRepeatable() {
        return false;
    }

    /**
     * Indicates whether the content is being streamed.
     *
     * @return {@code true}, as this entity is designed for streaming content.
     */
    @Override
    public boolean isStreaming() {
        return true;
    }

    /**
     * Writes the content to the specified {@link OutputStream}.
     *
     * @param out the {@link OutputStream} to which the content will be written.
     * @throws IOException if an I/O error occurs while writing the content.
     */
    @Override
    public void writeTo(OutputStream out) throws IOException {
        if (contentLength != 0) {
            out.write(jsonContent.getBytes());
        }
    }

    /**
     * Closes the entity. This implementation does not hold resources that need explicit closure.
     *
     * @throws IOException if an error occurs while closing the entity.
     */
    @Override
    public void close() throws IOException {
    }
}
