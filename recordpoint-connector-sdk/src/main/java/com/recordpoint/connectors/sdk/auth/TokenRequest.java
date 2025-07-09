package com.recordpoint.connectors.sdk.auth;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.google.common.base.Preconditions;
import com.microsoft.aad.msal4j.MsalServiceException;
import com.recordpoint.connectors.sdk.http.exception.HttpResponseException;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class TokenRequest implements Closeable {
    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    private final String tenantId;
    private final String clientId;
    private final String secret;
    private final String scope;

    public TokenRequest(String tenantId, String clientId, String secret, String scope) {
        this.tenantId = tenantId;
        this.clientId = clientId;
        this.secret = secret;
        this.scope = scope;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public synchronized Token getToken() throws TokenResponseException {
        try {
            if (executor == null || executor.isShutdown()) {
                executor = Executors.newSingleThreadExecutor();
            }
            TokenCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                    .tenantId(this.tenantId)
                    .clientId(this.clientId)
                    .clientSecret(this.secret)
                    .executorService(executor)
                    .build();

            TokenRequestContext context = new TokenRequestContext();
            context.addScopes(this.scope);

            AccessToken jwt = clientSecretCredential.getTokenSync(context);
            Preconditions.checkNotNull(Objects.requireNonNull(jwt).getToken());
            return Token.from(jwt);
        } catch (MsalServiceException ex) {
            throw new TokenResponseException(
                    HttpResponseException.Builder()
                            .setStatusCode(ex.statusCode())
                            .setDetail(ex.getMessage()));
        }
    }

    @Override
    public void close() throws IOException {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    public static class Builder {

        String tenantId;

        String clientId;

        String secret;

        String scope;

        public Builder setTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        public Builder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder setSecret(String secret) {
            this.secret = secret;
            return this;
        }

        public Builder setScope(String scope) {
            this.scope = scope;
            return this;
        }

        public TokenRequest build() {
            return new TokenRequest(tenantId, clientId, secret, scope);
        }

    }

}
