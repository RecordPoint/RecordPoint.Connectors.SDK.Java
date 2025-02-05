package com.recordpoint.connectors.sdk.auth;

import java.io.Closeable;

/**
 * A TokenManager is responsible for retrieving, refreshing and resetting the authentication token that is used on each
 * request. If multithreading is to be used, this must be a thread-safe implementation. To support saving the token
 * or cache, it is designed as a <code>Closeable</code> and should typically be used in a <code>try-with</code>
 * statement.
 *
 * The simplest example of a TokenManager implementation that reads from an environment variable is:
 *
 * <pre>
 * {@code
 * public class EnvTokenManager implements TokenManager {
 *     @Override
 *     public String getAccessToken() throws TokenResponseException {
 *         if (System.getenv("RECORDPOINT_ACCESS_TOKEN") != null) {
 *             return System.getenv("RECORDPOINT_ACCESS_TOKEN");
 *         }
 *         throw new TokenResponseException("$RECORDPOINT_ACCESS_TOKEN env variable is not set");
 *     }
 *
 *     @Override
 *     public void resetToken() {
 *         System.err.println("Regenerate your authentication token and change the value of $RECORDPOINT_ACCESS_TOKEN");
 *     }
 *
 *     @Override
 *     public void close() {}
 * }
 * }
 * </pre>
 */
public interface TokenManager extends Closeable {
    /**
     * Retrieve a valid access token.
     *
     * This is called for every HHTP request so should use a cached value wherever
     * possible. This method should block until the token is refreshed or retrieved, if required. If multithreading is
     * used, consider how multiple requests will be handled such that multiple concurrent calls do not lead to multiple
     * new tokens being retrieved.
     *
     * @return A valid JWT token as a String
     * @throws TokenResponseException Thrown if any issue with retrieving an access token occurs
     */
    String getAccessToken() throws TokenResponseException;

    /**
     * Reset the token
     *
     * This is called if a 401/403 error occurs, or if the client wishes to attempt re-authentication (e.g., after a
     * {@link TokenResponseException} is thrown). THis should forcibly reset the token and clear the cache if supported.
     * Whether this method retrieves a new token, or if that work is left to the next call of {@link getAccessToken()}
     * is left as a decision of the implementer.
     *
     * @throws TokenResponseException Thrown if any issue with resetting the token occurs
     */
    void resetToken() throws TokenResponseException;
}
