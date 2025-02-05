package com.recordpoint.connectors.sdk.http;

/**
 * A factory for creating HTTP request instances.
 *
 * <p>This class provides utility methods to construct {@link HttpRequest} objects with predefined
 * HTTP methods (e.g., GET, POST, PUT) and initializes them with an {@link HttpRequestInitializer}
 * if provided.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public final class HttpRequestFactory {

    /**
     * HTTP transport used to build requests.
     */
    private final HttpTransport transport;

    /**
     * HTTP request initializer to configure requests or {@code null} if no initialization is needed.
     */
    private final HttpRequestInitializer initializer;

    /**
     * Constructs an {@code HttpRequestFactory} with the given transport and initializer.
     *
     * @param transport   the HTTP transport used to create requests
     * @param initializer the HTTP request initializer or {@code null} for none
     */
    HttpRequestFactory(HttpTransport transport, HttpRequestInitializer initializer) {
        this.transport = transport;
        this.initializer = initializer;
    }

    /**
     * Builds a new HTTP request with the specified method, URL, and content.
     *
     * @param requestMethod the HTTP method (e.g., "GET", "POST")
     * @param url           the URL for the request
     * @param content       the streaming content for the request body, or {@code null} if none
     * @return a new {@link HttpRequest} instance
     */
    public HttpRequest buildRequest(String requestMethod, String url, StreamingContent content) {
        HttpRequest request = transport.buildRequest(requestMethod, url);
        if (initializer != null) {
            initializer.initialize(request);
        }
        if (content != null) {
            request.setStreamingContent(content);
        }
        return request;
    }

    /**
     * Builds an HTTP GET request for the specified URL.
     *
     * @param url the URL for the GET request
     * @return a new {@link HttpRequest} instance configured for a GET request
     */
    public HttpRequest buildGetRequest(String url) {
        return buildRequest(HttpMethods.GET, url, null);
    }

    /**
     * Builds an HTTP POST request for the specified URL with the given content.
     *
     * @param url     the URL for the POST request
     * @param content the streaming content for the request body
     * @return a new {@link HttpRequest} instance configured for a POST request
     */
    public HttpRequest buildPostRequest(String url, StreamingContent content) {
        return buildRequest(HttpMethods.POST, url, content);
    }

    /**
     * Builds an HTTP PUT request for the specified URL with the given content.
     *
     * @param url     the URL for the PUT request
     * @param content the streaming content for the request body
     * @return a new {@link HttpRequest} instance configured for a PUT request
     */
    public HttpRequest buildPutRequest(String url, StreamingContent content) {
        return buildRequest(HttpMethods.PUT, url, content);
    }
}
