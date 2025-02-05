package com.recordpoint.connectors.sdk.auth;

import com.recordpoint.connectors.sdk.service.ServiceSettings;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MsalTokenManager implements TokenManager {
    private final AtomicReference<Token> currentToken = new AtomicReference<>();

    private final TokenRequest tokenRequest;

    public MsalTokenManager(ServiceSettings serviceSettings) {
        this.tokenRequest = new TokenRequest(serviceSettings.getTenantId(),
                serviceSettings.getClientId(), serviceSettings.getSecret(), serviceSettings.getScope());
    }

    public synchronized Token getCurrentToken() throws TokenResponseException {
        if (Objects.isNull(currentToken.get())) {
            currentToken.set(tokenRequest.getToken());
        } else if (!isValidToken()) {
            currentToken.set(tokenRequest.getToken());
        }
        return currentToken.get();
    }

    private boolean isValidToken() {
        return !Instant.now().isAfter(Instant.ofEpochSecond(currentToken.get().getExpiresInSeconds()));
    }

    @Override
    public String getAccessToken() throws TokenResponseException {
        return getCurrentToken().getAccessToken();
    }

    @Override
    public void resetToken() throws TokenResponseException {
        currentToken.set(tokenRequest.getToken());
    }

    @Override
    public void close() {
        try {
            tokenRequest.close();
        } catch (IOException ignored) {
        }
    }
}
