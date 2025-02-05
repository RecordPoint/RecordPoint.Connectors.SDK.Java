package com.recordpoint.connectors.sdk.auth;

import com.azure.core.credential.AccessToken;

public final class Token {

    private String accessToken;

    private String tokenType;

    private Long expiresInSeconds;

    private String refreshToken;

    private String scope;

    public static Token from(AccessToken accessToken) {
        Token newToken = new Token();
        newToken.setAccessToken(accessToken.getToken());
        newToken.setTokenType(accessToken.getTokenType());
        newToken.setExpiresInSeconds(accessToken.getExpiresAt().toEpochSecond());
        return newToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(Long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}
