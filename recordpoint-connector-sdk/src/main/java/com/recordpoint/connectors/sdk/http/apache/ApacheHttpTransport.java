package com.recordpoint.connectors.sdk.http.apache;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.impl.routing.SystemDefaultRoutePlanner;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.io.CloseMode;
import org.apache.hc.core5.io.ModalCloseable;
import com.recordpoint.connectors.sdk.http.HttpMethods;
import com.recordpoint.connectors.sdk.http.HttpTransport;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * An implementation of {@link HttpTransport} using the Apache HttpClient to send HTTP requests.
 * This transport class provides methods to create and send HTTP requests using various HTTP methods
 * such as GET, POST, PUT, DELETE, and more. It is designed for custom configurations of the Apache HttpClient,
 * including connection management, proxy handling, and retry mechanisms.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public final class ApacheHttpTransport extends HttpTransport {

    /**
     * Apache HTTP client.
     */
    private final HttpClient httpClient;

    /**
     * Constructor that uses {@link #newDefaultHttpClient()} for the Apache HTTP client.
     */
    public ApacheHttpTransport() {
        this(newDefaultHttpClient());
    }

    /**
     * Constructor that allows an alternative Apache HTTP client to be used.
     *
     * <p>If you choose to provide your own Apache HttpClient implementation, be sure that
     *
     * <ul>
     *   <li>HTTP version is set to 1.1.
     *   <li>Retries are disabled.
     * </ul>
     *
     * @param httpClient Apache HTTP client to use
     */
    public ApacheHttpTransport(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new instance of the Apache HTTP client that is used by the {@link
     * #ApacheHttpTransport()} constructor.
     *
     * <p>Settings:
     *
     * <ul>
     *   <li>The client connection manager is set to {@link PoolingHttpClientConnectionManager}.
     *   <li>The retry mechanism is turned off using {@link
     *       HttpClientBuilder#disableAutomaticRetries()}.
     *   <li>Redirects are turned off using {@link HttpClientBuilder#disableRedirectHandling}.
     *   <li>The route planner uses {@link SystemDefaultRoutePlanner} with {@link
     *       ProxySelector#getDefault()}, which uses the proxy settings from <a
     *       href="https://docs.oracle.com/javase/8/docs/api/java/net/doc-files/net-properties.html">system
     *       properties</a>.
     * </ul>
     *
     * @return new instance of the Apache HTTP client
     */
    public static HttpClient newDefaultHttpClient() {
        return newDefaultHttpClientBuilder().build();
    }

    /**
     * Creates a new Apache HTTP client builder that is used by the {@link #ApacheHttpTransport()}
     * constructor.
     *
     * <p>Settings:
     *
     * <ul>
     *   <li>The client connection manager is set to {@link PoolingHttpClientConnectionManager}.
     *   <li>The retry mechanism is turned off using {@link
     *       HttpClientBuilder#disableAutomaticRetries()}.
     *   <li>Redirects are turned off using {@link HttpClientBuilder#disableRedirectHandling}.
     *   <li>The route planner uses {@link SystemDefaultRoutePlanner} with {@link
     *       ProxySelector#getDefault()}, which uses the proxy settings from <a
     *       href="http://docs.oracle.com/javase/8/docs/api/java/net/doc-files/net-properties.html">system
     *       properties</a>.
     * </ul>
     *
     * @return new instance of the Apache HTTP client builder
     */
    public static HttpClientBuilder newDefaultHttpClientBuilder() {
        PoolingHttpClientConnectionManager connectionManager =
                PoolingHttpClientConnectionManagerBuilder.create()
                        .setSSLSocketFactory(SSLConnectionSocketFactory.getSocketFactory())
                        .setMaxConnTotal(200)
                        .setMaxConnPerRoute(20)
                        .setDefaultConnectionConfig(
                                ConnectionConfig.custom().setTimeToLive(-1, TimeUnit.MILLISECONDS).build())
                        .build();

        return HttpClients.custom()
                .useSystemProperties()
                .setConnectionManager(connectionManager)
                .setRoutePlanner(new SystemDefaultRoutePlanner(ProxySelector.getDefault()))
                .disableRedirectHandling()
                .disableAutomaticRetries();
    }

    @Override
    public boolean supportsMethod(String method) {
        return true;
    }

    @Override
    protected ApacheHttpRequest buildRequest(String method, String url) {
        HttpUriRequestBase requestBase;
        if (method.equals(HttpMethods.DELETE)) {
            requestBase = new HttpDelete(url);
        } else if (method.equals(HttpMethods.GET)) {
            requestBase = new HttpGet(url);
        } else if (method.equals(HttpMethods.HEAD)) {
            requestBase = new HttpHead(url);
        } else if (method.equals(HttpMethods.PATCH)) {
            requestBase = new HttpPatch(url);
        } else if (method.equals(HttpMethods.POST)) {
            requestBase = new HttpPost(url);
        } else if (method.equals(HttpMethods.PUT)) {
            requestBase = new HttpPut(url);
        } else if (method.equals(HttpMethods.TRACE)) {
            requestBase = new HttpTrace(url);
        } else if (method.equals(HttpMethods.OPTIONS)) {
            requestBase = new HttpOptions(url);
        } else {
            requestBase = new HttpUriRequestBase(method, URI.create(url));
        }
        return new ApacheHttpRequest(httpClient, requestBase);
    }

    /**
     * Gracefully shuts down the connection manager and releases allocated resources. This closes all
     * connections, whether they are currently used or not.
     */
    @Override
    public void shutdown() throws IOException {
        if (httpClient instanceof ModalCloseable) {
            ((ModalCloseable) httpClient).close(CloseMode.GRACEFUL);
        }
    }

    /**
     * Returns the Apache HTTP client.
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

}
