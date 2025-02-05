package com.recordpoint.connectors.sdk.service;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.auth.TokenManager;
import com.recordpoint.connectors.sdk.auth.TokenRequest;
import com.recordpoint.connectors.sdk.auth.TokenResponseException;
import com.recordpoint.connectors.sdk.http.*;
import com.recordpoint.connectors.sdk.http.apache.ApacheHttpTransport;
import com.recordpoint.connectors.sdk.http.exception.HttpExecutionException;
import com.recordpoint.connectors.sdk.http.exception.HttpForbiddenException;
import com.recordpoint.connectors.sdk.http.exception.HttpResponseException;
import com.recordpoint.connectors.sdk.json.JsonMapper;
import com.recordpoint.connectors.sdk.json.JsonMapperException;
import com.recordpoint.connectors.sdk.json.jackson.JacksonMapper;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * Abstract base class for building service clients that communicate with RecordPoint REST APIs through {@link HttpRequest}.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public abstract class AbstractServiceClient {
    /**
     * Factory for creating {@link HttpRequest} requests.
     */
    private final HttpRequestFactory requestFactory;
    /**
     * Base URL for the root of the service.
     */
    private final String rootUrl;
    /**
     * {@link JsonMapper} mapper used for serializing and deserializing JSON content.
     */
    private final JsonMapper jsonMapper;
    /**
     * {@link TokenRequest} instance for obtaining an access token.
     */
    private final TokenManager tokenManager;

    /**
     * Constructs an instance of {@code AbstractServiceClient} with the specified builder.
     *
     * @param builder the builder containing necessary configurations.
     */
    protected AbstractServiceClient(Builder builder) {
        Preconditions.checkNotNull(builder.serviceSettings, "Service settings cannot be null");
        Preconditions.checkNotNull(builder.serviceSettings.getBaseUrl(), "Base URL in ServiceSettings cannot be null");
        this.rootUrl = builder.serviceSettings.getBaseUrl();
        this.requestFactory = builder.getTransport().createRequestFactory(builder.getRequestInitializer());
        this.jsonMapper = builder.getJsonParser();
        this.tokenManager = builder.getTokenManager();
    }

    private static <T extends ServicePayload> StreamingContent getStreamingContent(T payload) {
        return new StreamingContent() {
            @Override
            public InputStream getInputStream() throws IOException {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                // Create an ObjectOutputStream to serialize the object
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                    objectOutputStream.writeObject(payload); // Write the object to the stream
                }

                // Convert the byte array into an InputStream (ByteArrayInputStream)
                return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            }

            @Override
            public Object getContent() {
                return payload;
            }
        };
    }

    /**
     * Gets the HTTP request factory.
     *
     * @return {@link HttpRequestFactory} instance factory.
     */
    protected HttpRequestFactory getRequestFactory() {
        return requestFactory;
    }

    /**
     * Gets the root URL of the service.
     *
     * @return the root URL.
     */
    protected String getRootUrl() {
        return rootUrl;
    }

    /**
     * Gets the JSON mapper.
     *
     * @return {@link JsonMapper} instance mapper.
     */
    protected JsonMapper getJsonMapper() {
        return jsonMapper;
    }

    /**
     * Sends a GET request and retrieves a list of {@link ServiceResponse} responses.
     *
     * @param resourceUrl  the URL of the resource.
     * @param responseType the type of the response.
     * @param <T>          the type of service response.
     * @return a list of responses.
     * @throws HttpResponseException if an error occurs in the request.
     */
    protected final <T extends ServiceResponse> List<T> getRequestList(String resourceUrl, Class<T> responseType) throws HttpResponseException, JsonMapperException, HttpExecutionException {
        HttpRequest httpRequest = this.getRequestFactory().buildGetRequest(resourceUrl);
        return processRequest(httpRequest).parseListAs(responseType);
    }

    /**
     * Sends a GET request and retrieves a single {@link ServiceResponse} response.
     *
     * @param resourceUrl  the URL of the resource.
     * @param responseType the type of the response.
     * @param <T>          the type of service response.
     * @return the response.
     * @throws HttpResponseException if an error occurs in the request.
     */
    protected final <T extends ServiceResponse> T getRequest(String resourceUrl, Class<T> responseType) throws HttpResponseException, JsonMapperException, HttpExecutionException {
        HttpRequest httpRequest = this.getRequestFactory().buildGetRequest(resourceUrl);
        return processRequest(httpRequest).parseAs(responseType);
    }

    /**
     * Sends a POST request with a payload and retrieves a {@link ServiceResponse} response.
     *
     * @param resourceUrl  the URL of the resource.
     * @param payload      the payload to send as a type of {@link ServicePayload}.
     * @param responseType the type of the response.
     * @param <T>          the type of service response.
     * @param <P>          the type of service payload extended from {@link ServicePayload}.
     * @return the response.
     * @throws HttpResponseException if an error occurs in the request.
     */
    protected final <T extends ServiceResponse, P extends ServicePayload> T postRequest(String resourceUrl, P payload, Class<T> responseType) throws HttpResponseException, JsonMapperException, HttpExecutionException {
        HttpRequest httpRequest = this.getRequestFactory().buildPostRequest(resourceUrl, getStreamingContent(payload));
        httpRequest.setContentType("application/json");
        httpRequest.addHeader("Content-Type", "application/json");
        return processRequest(httpRequest).parseAs(responseType);
    }

    /**
     * Sends a POST request with a payload and retrieves a {@link ServiceResponse} response.
     *
     * @param resourceUrl the URL of the resource.
     * @param payload     the payload to send as a type of {@link ServicePayload}.
     * @param <T>         the type of service payload extended from {@link ServicePayload}.
     * @throws HttpResponseException if an error occurs in the request.
     */
    protected final <T extends ServicePayload> void postRequest(String resourceUrl, T payload) throws HttpResponseException, HttpExecutionException {
        HttpRequest httpRequest = this.getRequestFactory().buildPostRequest(resourceUrl, getStreamingContent(payload));
        httpRequest.setContentType("application/json");
        httpRequest.addHeader("Content-Type", "application/json");
        processRequest(httpRequest);
    }

    /**
     * Sends a PUT request with a payload and retrieves a {@link ServiceResponse} response.
     *
     * @param resourceUrl the URL of the resource.
     * @param payload     the payload to send as a type of {@link ServicePayload}.
     * @param <T>         the type of service payload extended from {@link ServicePayload}.
     * @throws HttpResponseException if an error occurs in the request.
     */
    protected final <T extends ServicePayload> void putRequest(String resourceUrl, T payload) throws HttpExecutionException, HttpResponseException {
        HttpRequest httpRequest = this.getRequestFactory().buildPutRequest(resourceUrl, getStreamingContent(payload));
        httpRequest.setContentType("application/json");
        httpRequest.addHeader("Content-Type", "application/json");
        processRequest(httpRequest);
    }

    /**
     * Sends a PUT request with a payload and retrieves a {@link ServiceResponse} response.
     *
     * @param resourceUrl  the URL of the resource.
     * @param payload      the payload to send as a type of {@link ServicePayload}.
     * @param responseType the type of the response.
     * @param <T>          the type of service response.
     * @param <P>          the type of service payload extended from {@link ServicePayload}.
     * @return the response.
     * @throws HttpResponseException if an error occurs in the request.
     */
    protected final <T extends ServiceResponse, P extends ServicePayload> T putRequest(String resourceUrl, P payload, Class<T> responseType) throws HttpResponseException, JsonMapperException, HttpExecutionException {
        HttpRequest httpRequest = this.getRequestFactory().buildPutRequest(resourceUrl, getStreamingContent(payload));
        httpRequest.setContentType("application/json");
        httpRequest.addHeader("Content-Type", "application/json");
        return processRequest(httpRequest).parseAs(responseType);
    }

    /**
     * Sends a PUT request to an external url resource.
     *
     * @param externalUrl   the external URL of the request.
     * @param streamContent the stream content of the request.
     * @throws HttpResponseException if an error occurs in the request.
     */
    protected void putExternalRequest(String externalUrl, StreamingContent streamContent) throws HttpResponseException, HttpExecutionException {
        HttpRequest httpRequest = this.getRequestFactory().buildPutRequest(externalUrl, streamContent);
        httpRequest.setContentType("multipart/form-data");
        httpRequest.setJsonMapper(getJsonMapper());
        httpRequest.addHeader("x-ms-blob-type", "BlockBlob");
        httpRequest.execute();
    }

    /**
     * Sets the Bearer token in the HTTP request headers.
     *
     * @param request the {@link HttpRequest} request.
     */
    private void setBearerHeader(HttpRequest request) throws TokenResponseException {
        request.addHeader("Authorization", "Bearer " + tokenManager.getAccessToken());
    }

    private HttpResponse processRequest(HttpRequest request) throws HttpResponseException, HttpExecutionException {
        try {
            request.setJsonMapper(getJsonMapper());
            setBearerHeader(request);
            return request.execute();
        } catch (HttpForbiddenException e) {
            tokenManager.resetToken();
            setBearerHeader(request);
            return request.execute();
        }
    }

    /**
     * Builder class for constructing instances of {@link AbstractServiceClient}.
     */
    public abstract static class Builder {

        /**
         * {@link HttpTransport} transport mechanism.
         */
        HttpTransport transport;

        /**
         * {@link HttpRequestInitializer} Initializer for HTTP requests.
         */
        HttpRequestInitializer initializer;

        /**
         * {@link JsonMapper} mapper.
         */
        JsonMapper mapper;

        /**
         * {@link ServiceSettings} settings.
         */
        ServiceSettings serviceSettings;

        /**
         * {@link TokenManager} TokenManager instance
         */
        TokenManager tokenManager;

        /**
         * Builds an instance of {@link AbstractServiceClient}.
         *
         * @param <T> the type of the service client.
         * @return a new instance of the service client.
         */
        public abstract <T extends AbstractServiceClient> T build();

        /**
         * Gets the HTTP transport.
         *
         * <p>
         * If no custom {@link HttpTransport} has been set, this method
         * provides a default implementation using {@link ApacheHttpTransport}.
         * </p>
         *
         * @return the configured {@link HttpTransport} instance, or a default
         * {@link ApacheHttpTransport} if none is set.
         */
        protected final HttpTransport getTransport() {
            return Objects.isNull(transport) ? new ApacheHttpTransport() : transport;
        }

        /**
         * Sets the HTTP transport.
         *
         * @param transport the {@link HttpTransport} transport.
         * @return this builder.
         */
        public Builder setTransport(HttpTransport transport) {
            this.transport = transport;
            return this;
        }

        /**
         * Gets the request initializer.
         *
         * @return the {@link HttpRequestInitializer} initializer, or {@code null} if not set.
         */
        protected final HttpRequestInitializer getRequestInitializer() {
            return Objects.isNull(initializer) ? null : initializer;
        }

        /**
         * Sets the request initializer.
         *
         * @param initializer the {@link HttpRequestInitializer} initializer.
         * @return this builder.
         */
        public Builder setRequestInitializer(HttpRequestInitializer initializer) {
            this.initializer = initializer;
            return this;
        }

        /**
         * Gets the JSON parser.
         *
         * @return the {@link JsonMapper} parser.
         */
        protected final JsonMapper getJsonParser() {
            return Objects.isNull(mapper) ? new JacksonMapper() : mapper;
        }

        /**
         * Gets the TokenManager.
         *
         * @return the {@link TokenManager} TokenManager.
         */
        protected final TokenManager getTokenManager() {
            Preconditions.checkNotNull(tokenManager, "TokenManager has not been set");
            return tokenManager;
        }

        /**
         * Sets the token manager.
         *
         * @param tokenManager the {@link TokenManager} TokenManager instance.
         * @return this builder.
         */
        public Builder setTokenManager(TokenManager tokenManager) {
            this.tokenManager = tokenManager;
            return this;
        }

        /**
         * Sets the JSON mapper.
         *
         * @param mapper the {@link JsonMapper} mapper.
         * @return this builder.
         */
        public Builder setJsonMapper(JsonMapper mapper) {
            this.mapper = mapper;
            return this;
        }

        /**
         * Sets the service settings.
         *
         * @param serviceSettings the {@link ServiceSettings} settings.
         * @return this builder.
         */
        public Builder setServiceSettings(ServiceSettings serviceSettings) {
            this.serviceSettings = serviceSettings;
            return this;
        }

    }

}
