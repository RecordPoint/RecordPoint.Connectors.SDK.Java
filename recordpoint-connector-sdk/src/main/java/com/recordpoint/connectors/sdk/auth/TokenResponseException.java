package com.recordpoint.connectors.sdk.auth;

import com.recordpoint.connectors.sdk.http.exception.HttpResponseException;

public class TokenResponseException extends HttpResponseException {

    public TokenResponseException(Builder builder) {
        super(builder);
    }

    public TokenResponseException(String message) {
        super(Builder().setDetail(message));
    }
}
