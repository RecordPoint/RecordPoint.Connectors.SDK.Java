package com.recordpoint.connectors.sdk.service.connector;

import com.recordpoint.connectors.sdk.http.exception.HttpExecutionException;
import com.recordpoint.connectors.sdk.http.exception.HttpResponseException;
import com.recordpoint.connectors.sdk.json.JsonMapperException;
import com.recordpoint.connectors.sdk.service.AbstractServiceClient;
import com.recordpoint.connectors.sdk.service.ServiceSettings;
import com.recordpoint.connectors.sdk.service.model.ConnectorConfig;

import java.util.List;

public class ConnectorServiceClient extends AbstractServiceClient {

    public ConnectorServiceClient(Builder builder) {
        super(builder);
    }

    public static Builder Builder() {
        return new Builder();
    }

    public ConnectorConfig getMultiTenantedConfiguration(GetMultiTenantedConfigRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s?connectorId=%s", getRootUrl(), request.getServicePath(), request.getConnectorId());
        return getRequest(resourceUrl, ConnectorConfig.class);
    }

    public ConnectorConfig getConnectorConfiguration(GetConnectorConfigRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s/%s", getRootUrl(), request.getServicePath(), request.getId());
        return getRequest(resourceUrl, ConnectorConfig.class);
    }

    public List<ConnectorConfig> getConnectorConfigurations() throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s/connector/api/ConnectorConfigurations", getRootUrl());
        return getRequestList(resourceUrl, ConnectorConfig.class);
    }

    public static class Builder extends AbstractServiceClient.Builder {

        @Override
        public ConnectorServiceClient build() {
            return new ConnectorServiceClient(this);
        }

        @Override
        public ConnectorServiceClient.Builder setServiceSettings(ServiceSettings serviceSettings) {
            super.setServiceSettings(serviceSettings);
            return this;
        }

    }

}
