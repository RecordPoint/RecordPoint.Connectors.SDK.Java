package com.recordpoint.connectors.sdk.http.exception;

/**
 * Exception to represent a Forbidden or Unauthorized HTTP response error.
 *
 * @author Pedro Moran
 * @version 1.0.0
 */
public class HttpForbiddenException extends HttpResponseException {

    /**
     * Constructor that receives a {@link com.recordpoint.connectors.sdk.http.exception.HttpResponseException.Builder}.
     */
    public HttpForbiddenException(Builder builder) {
        super(builder);
    }

}
