package com.recordpoint.connectors.sdk.http.apache;


import com.google.common.io.ByteStreams;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.routing.RoutingSupport;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.io.entity.ByteArrayEntity;
import org.apache.hc.core5.util.Timeout;
import com.recordpoint.connectors.sdk.http.HttpRequest;
import com.recordpoint.connectors.sdk.http.HttpResponse;
import com.recordpoint.connectors.sdk.http.exception.HttpExecutionException;
import com.recordpoint.connectors.sdk.http.exception.HttpForbiddenException;
import com.recordpoint.connectors.sdk.http.exception.HttpResponseException;
import com.recordpoint.connectors.sdk.json.JsonMapperException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * A concrete implementation of {@link HttpRequest} that uses Apache HttpClient for making HTTP requests.
 * This class provides methods for adding headers, setting timeouts, and executing HTTP requests
 * using the Apache HttpClient API.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public final class ApacheHttpRequest extends HttpRequest {

    /**
     * The HTTP request to be executed.
     */
    private final HttpUriRequestBase request;

    /**
     * The builder for configuring request timeouts and redirects.
     */
    private final RequestConfig.Builder requestConfig;

    /**
     * The Apache HttpClient instance used to execute the request.
     */
    private final HttpClient httpClient;

    /**
     * Constructs an {@link ApacheHttpRequest} with the specified {@link HttpClient} and {@link HttpUriRequestBase}.
     * Redirects are disabled as Google HTTP Client handles redirects.
     *
     * @param httpClient the Apache {@link HttpClient} instance to execute the request.
     * @param request    the {@link HttpUriRequestBase} representing the HTTP request to be executed.
     */
    ApacheHttpRequest(HttpClient httpClient, HttpUriRequestBase request) {
        super(request.getMethod());
        this.httpClient = httpClient;
        this.request = request;
        this.requestConfig = RequestConfig.custom().setRedirectsEnabled(false);
    }

    /**
     * Adds a header to the HTTP request.
     *
     * @param name  the name of the header.
     * @param value the value of the header.
     */
    @Override
    public void addHeader(String name, String value) {
        request.addHeader(name, value);
    }

    /**
     * Sets the connection and read timeouts for the HTTP request.
     *
     * @param connectTimeout the connection timeout in milliseconds.
     * @param readTimeout    the read timeout in milliseconds.
     * @throws IOException if an error occurs while setting the timeouts.
     */
    @Override
    public void setTimeout(int connectTimeout, int readTimeout) throws IOException {
        requestConfig
                .setConnectTimeout(Timeout.of(connectTimeout, TimeUnit.MILLISECONDS))
                .setResponseTimeout(Timeout.of(readTimeout, TimeUnit.MILLISECONDS));
    }

    /**
     * Executes the HTTP request using Apache HttpClient.
     * If the request contains streaming content, an {@link ApacheContentEntity} is created and added to the request.
     * The request is executed and the response is returned. If the response is not successful, an exception is thrown.
     *
     * @return an {@link HttpResponse} representing the response to the request.
     * @throws HttpResponseException if an error occurs during request execution.
     */
    @Override
    public HttpResponse execute() throws HttpResponseException, HttpExecutionException {
        try {
            if (getStreamingContent() != null) {
                setUpContent();
            }
            request.setConfig(requestConfig.build());
            HttpHost target;
            try {
                target = RoutingSupport.determineHost(request);
            } catch (HttpException e) {
                throw new HttpExecutionException("The request's host is invalid.", e);
            }
            ClassicHttpResponse httpResponse = httpClient.executeOpen(target, request, null);
            ApacheHttpResponse response = new ApacheHttpResponse(this, request, httpResponse);
            if (!response.isSuccessful()) {
                if (response.isForbiddenOrUnauthorized()) {
                    throw new HttpForbiddenException(HttpResponseException.Builder()
                            .setStatusCode(response.getStatusCode())
                            .setDetail("Unauthorized Token"));
                }
                throw HttpResponseException.Builder()
                        .fromHttpResponse(response).build();
            }
            return response;
        } catch (IOException | JsonMapperException e) {
            throw new HttpResponseException.Builder()
                    .fromException(e).build();
        }
    }

    private void setUpContent() throws JsonMapperException, HttpExecutionException {
        if (getContentType().equals("application/json")) {
            this.request.setEntity(new ApacheContentEntity(getJsonContent(), getContentType(), getContentEncoding()));
        } else {
            try {
                this.request.setEntity(new ByteArrayEntity(ByteStreams.toByteArray(getStreamingContent().getInputStream()),
                        ContentType.APPLICATION_OCTET_STREAM));
            } catch (IOException e) {
                throw new HttpExecutionException(e);
            }
        }
    }

}
