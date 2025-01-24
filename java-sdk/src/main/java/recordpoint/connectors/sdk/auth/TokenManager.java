package recordpoint.connectors.sdk.auth;

import java.io.Closeable;

public interface TokenManager extends Closeable {
    String getAccessToken() throws TokenResponseException;

    void resetToken() throws TokenResponseException;
}
