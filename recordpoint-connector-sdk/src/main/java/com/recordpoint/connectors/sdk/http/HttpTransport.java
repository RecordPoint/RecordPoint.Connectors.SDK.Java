package com.recordpoint.connectors.sdk.http;

import java.io.IOException;
import java.util.Arrays;

/**
 * Abstract base class for HTTP transport implementations.
 * Provides the foundation for creating and managing HTTP requests and connections.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public abstract class HttpTransport {
    /**
     * Array of HTTP methods supported by this transport.
     */
    private static final String[] SUPPORTED_METHODS = {
            HttpMethods.DELETE, HttpMethods.GET, HttpMethods.POST, HttpMethods.PUT
    };

    static {
        // Ensures supported methods are sorted for efficient binary search operations.
        Arrays.sort(SUPPORTED_METHODS);
    }

    /**
     * Creates a new {@link HttpRequestFactory} with no specific initializer.
     *
     * @return a new instance of {@link HttpRequestFactory}.
     */
    public final HttpRequestFactory createRequestFactory() {
        return createRequestFactory(null);
    }

    /**
     * Creates a new {@link HttpRequestFactory} with the specified initializer.
     *
     * @param initializer the {@link HttpRequestInitializer} to initialize requests, or {@code null} if none.
     * @return a new instance of {@link HttpRequestFactory}.
     */
    public final HttpRequestFactory createRequestFactory(HttpRequestInitializer initializer) {
        return new HttpRequestFactory(this, initializer);
    }

    /**
     * Checks whether the given HTTP method is supported by this transport.
     *
     * @param method the HTTP method to check (e.g., "GET", "POST").
     * @return {@code true} if the method is supported; {@code false} otherwise.
     * @throws IOException if an I/O error occurs while performing the check.
     */
    public boolean supportsMethod(String method) throws IOException {
        return Arrays.binarySearch(SUPPORTED_METHODS, method) >= 0;
    }

    /**
     * Builds a new {@link HttpRequest} with the specified HTTP method and URL.
     * Subclasses must implement this method to provide their specific request creation logic.
     *
     * @param method the HTTP method (e.g., "GET", "POST").
     * @param url    the URL for the request.
     * @return a new {@link HttpRequest} instance.
     */
    protected abstract HttpRequest buildRequest(String method, String url);

    /**
     * Shuts down the transport and releases any resources associated with it.
     * Default implementation does nothing, but subclasses may override this to clean up resources.
     *
     * @throws IOException if an I/O error occurs during shutdown.
     */
    public void shutdown() throws IOException {
    }

    /**
     * Indicates whether the transport has been shut down.
     *
     * @return {@code true} if the transport is shut down; {@code false} otherwise.
     */
    public boolean isShutdown() {
        return true;
    }
}
