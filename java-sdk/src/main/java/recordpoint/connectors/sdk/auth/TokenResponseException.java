package recordpoint.connectors.sdk.auth;

import recordpoint.connectors.sdk.http.exception.HttpResponseException;

public class TokenResponseException extends HttpResponseException {

    public TokenResponseException(Builder builder) {
        super(builder);
    }
}
