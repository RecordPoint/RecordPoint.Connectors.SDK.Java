package com.recordpoint.connectors.sdk.http;

/**
 * Interface for initializing HTTP requests.
 *
 * <p>Implementations of this interface can customize or configure an {@link HttpRequest} instance
 * before it is executed. For example, this could include setting headers, configuring timeouts,
 * or attaching authentication details.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public interface HttpRequestInitializer {

    /**
     * Initializes the given HTTP request.
     *
     * <p>This method is invoked before the execution of the HTTP request, allowing implementations
     * to perform necessary setup or configuration. It is recommended to ensure that the request is
     * in a valid state after initialization.
     *
     * @param request the {@link HttpRequest} to initialize
     */

    void initialize(HttpRequest request);

}
