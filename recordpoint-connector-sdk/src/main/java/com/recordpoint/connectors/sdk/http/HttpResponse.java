package com.recordpoint.connectors.sdk.http;

import com.recordpoint.connectors.sdk.json.JsonMapperException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * Represents an HTTP response received from an {@link HttpRequest}.
 *
 * <p>This class provides methods to access response metadata such as headers, status codes, and
 * content. Subclasses must implement methods to interact with specific HTTP transport mechanisms.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public abstract class HttpResponse {

    /**
     * The associated {@link HttpRequest} that generated this response.
     */
    private final HttpRequest request;

    /**
     * Parsed HTTP media type from the "Content-Type" header or {@code null} if unavailable.
     */
    private final HttpMediaType mediaType;

    /**
     * Creates a new {@code HttpResponse} with the given request and content type.
     *
     * @param request     the associated HTTP request
     * @param contentType the "Content-Type" of the response, or {@code null} if not provided
     */
    public HttpResponse(HttpRequest request, String contentType) {
        this.request = request;
        this.mediaType = parseMediaType(contentType);
    }

    /**
     * Parses the media type from the given content type string.
     *
     * @param contentType the "Content-Type" string
     * @return the parsed {@link HttpMediaType} or {@code null} if the content type is invalid or not provided
     */
    private HttpMediaType parseMediaType(String contentType) {
        if (contentType == null) {
            return null;
        }
        try {
            return new HttpMediaType(contentType);
        } catch (IllegalArgumentException e) {
            return null; // Invalid content type
        }
    }

    /**
     * Returns the input stream of the HTTP response content or {@code null} if unavailable.
     *
     * @return the content input stream
     * @throws IOException if an I/O error occurs
     */
    public abstract InputStream getContent() throws IOException;

    /**
     * Returns the content encoding (e.g., {@code "gzip"}) or {@code null} if none.
     *
     * @return the content encoding
     * @throws IOException if an I/O error occurs
     */
    public abstract String getContentEncoding() throws IOException;

    /**
     * Returns the content length in bytes or {@code 0} if not available.
     *
     * @return the content length
     * @throws IOException if an I/O error occurs
     */
    public abstract long getContentLength() throws IOException;

    /**
     * Returns the content type (e.g., {@code "application/json"}) or {@code null} if none.
     *
     * @return the content type
     * @throws IOException if an I/O error occurs
     */
    public abstract String getContentType() throws IOException;

    /**
     * Returns the status line of the response or {@code null} if not available.
     *
     * @return the response status line
     * @throws IOException if an I/O error occurs
     */
    public abstract String getStatusLine() throws IOException;

    /**
     * Returns the HTTP status code of the response or a value {@code <= 0} if unavailable.
     *
     * @return the HTTP status code
     */
    public abstract int getStatusCode();

    /**
     * Returns the HTTP reason phrase (e.g., "OK") or {@code null} if not available.
     *
     * @return the HTTP reason phrase
     * @throws IOException if an I/O error occurs
     */
    public abstract String getReasonPhrase() throws IOException;

    /**
     * Returns the number of HTTP response headers.
     *
     * <p>Supports multiple headers with the same name.
     *
     * @return the number of headers
     * @throws IOException if an I/O error occurs
     */
    public abstract int getHeaderCount() throws IOException;

    /**
     * Returns the name of the header at the given zero-based index.
     *
     * @param index the header index
     * @return the header name
     * @throws IOException if an I/O error occurs
     */
    public abstract String getHeaderName(int index) throws IOException;

    /**
     * Returns the value of the header at the given zero-based index.
     *
     * @param index the header index
     * @return the header value
     * @throws IOException if an I/O error occurs
     */
    public abstract String getHeaderValue(int index) throws IOException;

    /**
     * Returns the associated {@link HttpRequest}.
     *
     * @return the HTTP request
     */
    public HttpRequest getRequest() {
        return request;
    }

    /**
     * Closes the response content input stream, ignoring any remaining content.
     *
     * @throws IOException if an I/O error occurs
     */
    public void ignore() throws IOException {
        InputStream content = getContent();
        if (content != null) {
            content.close();
        }
    }

    /**
     * Determines if the response contains a message body.
     *
     * @return {@code true} if the response has a body, otherwise {@code false}
     * @throws IOException if an I/O error occurs
     */
    private boolean hasMessageBody() throws IOException {
        int statusCode = getStatusCode();
        long contentLength = getContentLength();
        if ((getRequest().getRequestMethod().equals(HttpMethods.HEAD)
                || statusCode / 100 == 1
                || statusCode == HttpStatusCodes.STATUS_CODE_NO_CONTENT
                || statusCode == HttpStatusCodes.STATUS_CODE_NOT_MODIFIED
                || statusCode == HttpStatusCodes.STATUS_CODE_ACCEPTED)
                && contentLength == 0) {
            ignore();
            return false;
        }
        return true;
    }

    /**
     * Parses the response content as an object of the specified class.
     *
     * @param <T>              the type of the object
     * @param destinationClass the destination class
     * @return the parsed object, or {@code null} if no content
     * @throws JsonMapperException if an error occurs during the parse process.
     */
    public <T> T parseAs(Class<T> destinationClass) throws JsonMapperException {
        try {
            if (!hasMessageBody() || Objects.isNull(destinationClass)) {
                return null;
            }
            return request.getJsonMapper().parseAndClose(getContent(), getContentCharset(), destinationClass);
        } catch (IOException e) {
            throw new JsonMapperException(e, destinationClass);
        }
    }

    /**
     * Parses the response content as a list of objects of the specified class.
     *
     * @param <T>              the type of the objects in the list.
     * @param destinationClass the class of the list elements.
     * @return the parsed list, or {@code null} if no content.
     * @throws JsonMapperException if an error occurs during the parse process.
     */
    public <T> List<T> parseListAs(Class<T> destinationClass) throws JsonMapperException {
        try {
            if (!hasMessageBody()) {
                return null;
            }
            return request.getJsonMapper().parseAsList(getContent(), getContentCharset(),
                    destinationClass);
        } catch (IOException e) {
            throw new JsonMapperException(e, destinationClass);
        }
    }

    /**
     * Returns the character set of the response content or a default value.
     *
     * @return the content charset
     */
    public Charset getContentCharset() {
        if (mediaType != null) {
            if (mediaType.getCharsetParameter() != null) {
                return mediaType.getCharsetParameter();
            }
            if ("application".equals(mediaType.getType()) && "json".equals(mediaType.getSubType())) {
                return StandardCharsets.UTF_8;
            }
            if ("text".equals(mediaType.getType()) && "csv".equals(mediaType.getSubType())) {
                return StandardCharsets.UTF_8;
            }
        }
        return StandardCharsets.ISO_8859_1;
    }

    /**
     * Returns whether the response is successful (status code 2xx).
     *
     * @return {@code true} if the status code is 2xx, otherwise {@code false}
     * @throws IOException if an I/O error occurs
     */
    public boolean isSuccessful() throws IOException {
        return HttpStatusCodes.isSuccess(getStatusCode());
    }

    /**
     * Returns whether the response is forbidden or unauthorized (status code 401 or 403).
     *
     * @return {@code true} if the status code is 401 or 403, otherwise {@code false}
     * @throws IOException if an I/O error occurs
     */
    public boolean isForbiddenOrUnauthorized() throws IOException {
        return HttpStatusCodes.isForbiddenOrUnauthorized(getStatusCode());
    }

    /**
     * Attempts to release resources associated with the response.
     *
     * <p>Subclasses may override this to implement specific resource cleanup.
     *
     * @throws IOException if an I/O error occurs
     */
    public void disconnect() throws IOException {
    }
}
