package com.recordpoint.connectors.sdk.http.exception;

import com.google.common.io.CharStreams;
import com.recordpoint.connectors.sdk.http.HttpResponse;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Exception to represent an HTTP response error.
 * Encapsulates details like HTTP status code, title, and error details.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public class HttpResponseException extends Exception {
    /**
     * HTTP status code of the error.
     */
    private final int statusCode;

    /**
     * Detailed message of the error.
     */
    private final String detail;

    /**
     * Constructs an {@code HttpResponseException} using the provided builder.
     *
     * @param builder the builder containing error details.
     */
    public HttpResponseException(Builder builder) {
        super(builder.detail);
        this.statusCode = builder.statusCode;
        this.detail = builder.detail;
    }

    /**
     * Creates a new {@link Builder} instance.
     *
     * @return a new builder.
     */
    public static Builder Builder() {
        return new Builder();
    }

    /**
     * Returns the HTTP status code of the error.
     *
     * @return the HTTP status code.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the detailed message of the error.
     *
     * @return the detailed message.
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Builder for {@link HttpResponseException}.
     * Allows step-by-step construction of an exception instance.
     */
    public static class Builder {

        /**
         * HTTP status code of the error.
         */
        int statusCode;

        /**
         * Detailed message of the error.
         */
        String detail;

        /**
         * Populates the builder using the provided HTTP response.
         *
         * @param response the HTTP response to extract details from.
         * @return the builder instance.
         */
        public Builder fromHttpResponse(HttpResponse response) {
            try {
                String body = CharStreams.toString(
                        new InputStreamReader(response.getContent(), response.getContentCharset())
                );
                this.statusCode = response.getStatusCode();
                this.detail = "API request failed with status code " + response.getStatusCode() + (
                        body.isEmpty() ? "" : " and body: " + body
                );
            } catch (IOException e) {
                return setDefaultError(response.getStatusCode(), e.getMessage());
            }
            return this;
        }

        public Builder fromException(Exception exception) {
            return setDefaultError(-1, exception.getMessage());
        }

        /**
         * Sets the HTTP status code.
         *
         * @param statusCode the HTTP status code.
         * @return the builder instance.
         */
        public Builder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        /**
         * Sets the detailed message of the error.
         *
         * @param detail the detailed message.
         * @return the builder instance.
         */
        public Builder setDetail(String detail) {
            this.detail = detail;
            return this;
        }

        /**
         * Sets a default error message for unexpected errors.
         *
         * @param statusCode      the HTTP status code.
         * @param unexpectedError the unexpected error message.
         * @return the builder instance.
         */
        private Builder setDefaultError(int statusCode, String unexpectedError) {
            this.statusCode = statusCode;
            this.detail = unexpectedError;
            return this;
        }

        /**
         * Builds and returns an {@link HttpResponseException} instance.
         *
         * @return a new instance of {@link HttpResponseException}.
         */
        public HttpResponseException build() {
            return new HttpResponseException(this);
        }
    }
}
