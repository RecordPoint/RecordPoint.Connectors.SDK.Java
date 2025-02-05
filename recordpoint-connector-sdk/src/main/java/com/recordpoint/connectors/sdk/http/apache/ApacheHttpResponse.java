package com.recordpoint.connectors.sdk.http.apache;

import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.message.StatusLine;
import com.recordpoint.connectors.sdk.http.HttpRequest;
import com.recordpoint.connectors.sdk.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * A concrete implementation of {@link HttpResponse} that wraps the response from Apache HttpClient.
 * This class provides methods to access various details of the HTTP response, such as status code,
 * headers, content, and entity. It also supports disconnecting and aborting the request execution.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
final class ApacheHttpResponse extends HttpResponse {

    /**
     * The original Apache HTTP request.
     */
    private final HttpUriRequestBase request;

    /**
     * The Apache HttpClient response.
     */
    private final ClassicHttpResponse response;

    /**
     * All headers present in the response.
     */
    private final Header[] allHeaders;

    /**
     * The HTTP entity containing the response body.
     */
    private final HttpEntity entity;

    /**
     * Constructs an {@link ApacheHttpResponse} using the given request, Apache HTTP request, and response.
     *
     * @param request       the {@link HttpRequest} that initiated the request.
     * @param apacheRequest the {@link HttpUriRequestBase} representing the Apache HTTP request.
     * @param response      the {@link ClassicHttpResponse} received from Apache HttpClient.
     */
    ApacheHttpResponse(HttpRequest request, HttpUriRequestBase apacheRequest, ClassicHttpResponse response) {
        super(request, Optional.ofNullable(response.getEntity()).map(HttpEntity::getContentType).orElse("application/json"));
        this.request = apacheRequest;
        this.response = response;
        this.allHeaders = response.getHeaders();
        this.entity = response.getEntity();
    }

    /**
     * Returns the HTTP status code from the response.
     *
     * @return the HTTP status code.
     */
    @Override
    public int getStatusCode() {
        return response.getCode();
    }

    /**
     * Returns the content of the HTTP response as an {@link InputStream}.
     * Wraps the response entity's content in an {@link ApacheResponseContent}.
     *
     * @return an {@link InputStream} containing the response content.
     * @throws IOException if an I/O error occurs while reading the content.
     */
    @Override
    public InputStream getContent() throws IOException {
        InputStream content = entity == null ? null : entity.getContent();
        return new ApacheResponseContent(content, response);
    }

    /**
     * Returns the content encoding of the HTTP response.
     *
     * @return the content encoding, or {@code null} if not available.
     */
    @Override
    public String getContentEncoding() {
        return entity != null ? entity.getContentEncoding() : null;
    }

    /**
     * Returns the content length of the HTTP response.
     *
     * @return the content length, or -1 if unknown.
     */
    @Override
    public long getContentLength() {
        return entity == null ? -1 : entity.getContentLength();
    }

    /**
     * Returns the content type of the HTTP response.
     *
     * @return the content type, or {@code null} if not available.
     */
    @Override
    public String getContentType() {
        return entity == null ? null : entity.getContentType();
    }

    /**
     * Returns the reason phrase associated with the HTTP response status code.
     *
     * @return the reason phrase.
     */
    @Override
    public String getReasonPhrase() {
        return response.getReasonPhrase();
    }

    /**
     * Returns the status line of the HTTP response, including the status code and reason phrase.
     *
     * @return the status line as a string.
     */
    @Override
    public String getStatusLine() {
        return new StatusLine(response).toString();
    }

    /**
     * Returns the value of the specified header from the HTTP response.
     *
     * @param name the name of the header.
     * @return the value of the specified header, or {@code null} if the header is not found.
     */
    public String getHeaderValue(String name) {
        return response.getLastHeader(name).getValue();
    }

    /**
     * Returns the number of headers in the HTTP response.
     *
     * @return the number of headers.
     */
    @Override
    public int getHeaderCount() {
        return allHeaders.length;
    }

    /**
     * Returns the name of the header at the specified index.
     *
     * @param index the index of the header.
     * @return the name of the header.
     */
    @Override
    public String getHeaderName(int index) {
        return allHeaders[index].getName();
    }

    /**
     * Returns the value of the header at the specified index.
     *
     * @param index the index of the header.
     * @return the value of the header.
     */
    @Override
    public String getHeaderValue(int index) {
        return allHeaders[index].getValue();
    }

    /**
     * Aborts the execution of the request and closes the response entity.
     * This method is used to terminate the connection and free resources.
     *
     * @throws IOException if an I/O error occurs during disconnect.
     */
    @Override
    public void disconnect() throws IOException {
        request.abort();
        response.close();
    }
}
