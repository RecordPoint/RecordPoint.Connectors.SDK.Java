package com.recordpoint.connectors.sdk.http.exception;

public class HttpExecutionException extends Exception {

    public HttpExecutionException(Exception e) {
        super("There was an error during the execution request", e);
    }

    public HttpExecutionException(String message, Exception e) {
        super(message, e);
    }

}
