package com.recordpoint.connectors.sdk.service;

import com.recordpoint.connectors.sdk.json.JsonMapperException;
import com.recordpoint.connectors.sdk.service.util.SettingUtil;

import java.nio.file.Path;
import java.util.Map;

/**
 * Configuration class for setting up the SDK with the necessary
 * service details for the Recorpoint platform.
 * <p>
 * This class encapsulates all the essential settings required
 * for authentication and service communication, such as base URL,
 * client credentials, and scope. Instances of this class are
 * built using the {@link Builder} pattern.
 * </p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * ServiceSettings settings = ServiceSettings.Builder()
 *     .setBaseUrl("https://api.recorpoint.com")
 *     .setTenantId("tenant123")
 *     .setClientId("client456")
 *     .setSecret("secret789")
 *     .setScope("read write")
 *     .setConnectorId("connector001")
 *     .build();
 * }</pre>
 *
 * @see Builder
 */
public final class ServiceSettings {
    private static final String DEFAULT_SCOPE = "https://management.azure.com/.default";
    private final String baseUrl;

    private final String tenantId;
    private final String clientId;
    private final String secret;
    private final String scope;
    private final String connectorId;

    /**
     * Constructs a {@link ServiceSettings} instance using the provided builder.
     *
     * @param builder the {@link Builder} instance containing configuration values.
     */
    public ServiceSettings(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.clientId = builder.clientId;
        this.tenantId = builder.tenantId;
        this.secret = builder.secret;
        this.scope = builder.scope != null ? builder.scope : DEFAULT_SCOPE;
        this.connectorId = builder.connectorId;
    }

    /**
     * Creates a new {@link Builder} instance.
     *
     * @return a new {@link Builder}.
     */
    public static Builder Builder() {
        return new Builder();
    }

    /**
     * Gets the base URL for the service.
     *
     * @return the base URL.
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Gets the tenant ID.
     *
     * @return the tenant ID.
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Gets the client ID.
     *
     * @return the client ID.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Gets the client secret.
     *
     * @return the client secret.
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Gets the scope for the service.
     *
     * @return the scope.
     */
    public String getScope() {
        return scope;
    }

    /**
     * Gets the connector ID.
     *
     * @return the connector ID.
     */
    public String getConnectorId() {
        return connectorId;
    }

    public enum Regions {
        AUE("https://connector-aue.records365.com.au"),
        CAC("https://connector-cac.records365.ca"),
        UKS("https://connector-uks.records365.co.uk"),
        USW("https://connector-usw.records365.com");

        private final String url;

        Regions(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    /**
     * Builder class for {@link ServiceSettings}.
     * <p>
     * Provides a fluent API for constructing a {@link ServiceSettings}
     * instance with various configuration values.
     * </p>
     */
    public static class Builder {
        private String baseUrl;
        private String tenantId;
        private String clientId;
        private String secret;
        private String scope;
        private String connectorId;

        /**
         * Sets the base URL for the service.
         *
         * @param baseUrl the base URL.
         * @return this {@link Builder} instance.
         */
        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Sets the base URL for the service using a pre-defined region
         *
         * @param region the region to connect to
         * @return this {@link Builder} instance.
         */
        public Builder setRegion(Regions region) {
            this.baseUrl = region.getUrl();
            return this;
        }

        /**
         * Sets the tenant ID.
         *
         * @param tenantId the tenant ID.
         * @return this {@link Builder} instance.
         */
        public Builder setTenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        /**
         * Sets the client ID.
         *
         * @param clientId the client ID.
         * @return this {@link Builder} instance.
         */
        public Builder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        /**
         * Sets the client secret.
         *
         * @param secret the client secret.
         * @return this {@link Builder} instance.
         */
        public Builder setSecret(String secret) {
            this.secret = secret;
            return this;
        }

        /**
         * Sets the scope for the service.
         *
         * @param scope the scope.
         * @return this {@link Builder} instance.
         */
        public Builder setScope(String scope) {
            this.scope = scope;
            return this;
        }

        /**
         * Sets the connector ID.
         *
         * @param connectorId the connector ID.
         * @return this {@link Builder} instance.
         */
        public Builder setConnectorId(String connectorId) {
            this.connectorId = connectorId;
            return this;
        }

        /**
         * Sets all the settings from a json file.
         *
         * @param jsonFile path to the json file.
         * @return this {@link Builder} instance.
         * @throws JsonMapperException if there's an issue with the json file.
         */
        public Builder fromJsonFile(Path jsonFile) throws JsonMapperException {
            Map<String, String> settings = SettingUtil.getSettingsFromJsonFile(jsonFile);
            this.tenantId = settings.get("TenantId");
            this.baseUrl = settings.get("ConnectorApiUrl");
            this.clientId = settings.get("ClientId");
            this.secret = settings.get("ClientSecret");
            this.scope = settings.get("Audience");
            this.connectorId = settings.get("ConnectorId");
            return this;
        }

        /**
         * Builds a {@link ServiceSettings} instance with the configured values.
         *
         * @return a new {@link ServiceSettings} instance.
         */
        public ServiceSettings build() {
            return new ServiceSettings(this);
        }

    }

}
