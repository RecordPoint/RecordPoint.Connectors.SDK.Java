package com.recordpoint.connectors.sdk.http.exception;

import com.google.common.io.CharStreams;
import com.recordpoint.connectors.sdk.http.ErrorResponse;
import com.recordpoint.connectors.sdk.http.HttpResponse;
import com.recordpoint.connectors.sdk.json.JsonMapperException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
     * Title of the error.
     */
    private final String title;

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
        this.title = builder.title;
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
     * Returns the title of the error.
     *
     * @return the title.
     */
    public String getTitle() {
        return title;
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
         * Title of the error.
         */
        String title;

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
                if (response.getContentType().contains("json")) {
                    ErrorResponse error = response.parseAs(ErrorResponse.class);
                    this.statusCode = response.getStatusCode();
                    this.title = "API Error";
                    this.detail = error.getError().getMessage();
                } else {
                    setDefaultError(response.getStatusCode(), CharStreams.toString(new InputStreamReader(response.getContent(), StandardCharsets.UTF_8)));
                }
            } catch (IOException | JsonMapperException e) {
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
         * Sets the title of the error.
         *
         * @param title the title of the error.
         * @return the builder instance.
         */
        public Builder setTitle(String title) {
            this.title = title;
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
            this.title = "Unexpected Error";
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
