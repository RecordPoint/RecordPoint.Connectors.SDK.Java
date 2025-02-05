package com.recordpoint.connectors.sdk.http;

import com.recordpoint.connectors.sdk.http.exception.HttpExecutionException;
import com.recordpoint.connectors.sdk.http.exception.HttpResponseException;
import com.recordpoint.connectors.sdk.json.JsonMapper;
import com.recordpoint.connectors.sdk.json.JsonMapperException;

import java.io.IOException;

/**
 * Represents an abstract HTTP request.
 *
 * <p>This class provides the basic structure and functionality for creating and executing HTTP
 * requests. Subclasses are expected to implement specific behavior for headers and executing
 * requests.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public abstract class HttpRequest {

    /**
     * HTTP request method (e.g., {@code "GET"}, {@code "POST"}).
     */
    private final String requestMethod;
    /**
     * Content length or less than zero if not known.
     */
    private long contentLength = -1;
    /**
     * Content encoding (for example {@code "gzip"}) or {@code null} for none.
     */
    private String contentEncoding;
    /**
     * Content type or {@code null} for none.
     */
    private String contentType;
    /**
     * Streaming content or {@code null} for no content.
     */
    private StreamingContent streamingContent;
    /**
     * JSON mapper for serializing and deserializing JSON content.
     */
    private JsonMapper jsonMapper;

    /**
     * Constructs a new {@link HttpRequest}.
     *
     * @param requestMethod the HTTP request method (e.g., {@code "GET"}, {@code "POST"}).
     */
    public HttpRequest(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    /**
     * Adds a header to the HTTP request.
     *
     * <p>Note that multiple headers of the same name need to be supported, in which case {@link
     * #addHeader} will be called for each instance of the header.
     *
     * @param name  header name
     * @param value header value
     */
    public abstract void addHeader(String name, String value);

    /**
     * Returns the content length.
     *
     * @return the content length in bytes, or less than zero if not known.
     */
    public final long getContentLength() {
        return contentLength;
    }

    /**
     * Sets the content length.
     *
     * <p>A value of less than zero indicates the content length is unknown. The default value is
     * {@code -1}.
     *
     * @param contentLength the content length in bytes, or less than zero if not known.
     * @throws IOException if an I/O error occurs while setting the content length.
     */
    public final void setContentLength(long contentLength) throws IOException {
        this.contentLength = contentLength;
    }

    /**
     * Returns the content encoding.
     *
     * @return the content encoding (e.g., {@code "gzip"}), or {@code null} if none is set.
     */
    public final String getContentEncoding() {
        return contentEncoding;
    }

    /**
     * Sets the content encoding.
     *
     * @param contentEncoding the content encoding (e.g., {@code "gzip"}), or {@code null} for none.
     * @throws IOException if an I/O error occurs while setting the content encoding.
     */
    public final void setContentEncoding(String contentEncoding) throws IOException {
        this.contentEncoding = contentEncoding;
    }

    /**
     * Returns the HTTP request method.
     *
     * @return the HTTP request method (e.g., {@code "GET"}, {@code "POST"}).
     */
    public final String getRequestMethod() {
        return requestMethod;
    }

    /**
     * Returns the content type.
     *
     * @return the content type (e.g., {@code "application/json"}), or {@code null} if none is set.
     */
    public final String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type.
     *
     * @param contentType the content type (e.g., {@code "application/json"}), or {@code null} for
     *                    none.
     */
    public final void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Returns the streaming content.
     *
     * @return the streaming content, or {@code null} if no content is set.
     */
    public final StreamingContent getStreamingContent() {
        return streamingContent;
    }

    /**
     * Sets the streaming content.
     *
     * @param streamingContent the streaming content, or {@code null} for no content.
     */
    public final void setStreamingContent(StreamingContent streamingContent) {
        this.streamingContent = streamingContent;
    }

    /**
     * Retrieves the JSON representation of the streaming content.
     *
     * @return a string containing the JSON representation of the streaming content.
     * @throws JsonMapperException if an I/O error occurs while retrieving the JSON content.
     */
    public final String getJsonContent() throws JsonMapperException {
        return getStreamingContent().getJsonRepresentation(jsonMapper);
    }

    /**
     * Returns the JSON mapper.
     *
     * @return the {@link JsonMapper} used for JSON serialization and deserialization.
     */
    public final JsonMapper getJsonMapper() {
        return jsonMapper;
    }

    /**
     * Sets the JSON mapper.
     *
     * @param jsonMapper the {@link JsonMapper} to use for JSON serialization and deserialization.
     */
    public final void setJsonMapper(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    /**
     * Sets the connection and read timeouts for the request.
     *
     * <p>Subclasses should override this method to provide functionality. The default
     * implementation does nothing.
     *
     * @param connectTimeout the timeout in milliseconds to establish a connection, or {@code 0}
     *                       for an infinite timeout.
     * @param readTimeout    the timeout in milliseconds to read data from the connection, or {@code 0}
     *                       for an infinite timeout.
     * @throws IOException if an I/O error occurs while setting the timeouts.
     */
    public void setTimeout(int connectTimeout, int readTimeout) throws IOException {
    }

    /**
     * Sets the write timeout for POST/PUT requests.
     *
     * <p>Subclasses should override this method to provide functionality. The default
     * implementation does nothing.
     *
     * @param writeTimeout the timeout in milliseconds for writing data, or {@code 0} for an
     *                     infinite timeout.
     * @throws IOException if an I/O error occurs while setting the timeout.
     */
    public void setWriteTimeout(int writeTimeout) throws IOException {
    }

    /**
     * Executes the HTTP request and returns a response.
     *
     * @return a {@link HttpResponse} representing the result of the executed request.
     * @throws HttpResponseException if an error occurs during the execution of the request.
     */
    public abstract HttpResponse execute() throws HttpResponseException, HttpExecutionException;

}
