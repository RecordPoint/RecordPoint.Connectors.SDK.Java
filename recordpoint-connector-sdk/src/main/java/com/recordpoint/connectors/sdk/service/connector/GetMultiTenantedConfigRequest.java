package com.recordpoint.connectors.sdk.service.connector;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.model.ConnectorConfig;
import com.recordpoint.connectors.sdk.service.notification.GetNotificationRequest;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

public class GetMultiTenantedConfigRequest extends AbstractServiceRequest<ConnectorConfig> {

    private final String servicePath = "/connector/api/ConnectorConfigurations/GetMultiTenanted";

    private final String connectorId;

    public GetMultiTenantedConfigRequest(Builder builder) {
        Preconditions.checkNotNull(builder.connectorId, MessageFieldProvider.getMessage("field.connectorId"));
        this.connectorId = builder.connectorId;
    }

    public static GetNotificationRequest.Builder Builder() {
        return new GetNotificationRequest.Builder();
    }

    public String getConnectorId() {
        return connectorId;
    }

    public String getServicePath() {
        return servicePath;
    }

    public static class Builder extends AbstractServiceRequest.Builder {
        String connectorId;

        public GetMultiTenantedConfigRequest.Builder setConnectorId(String connectorId) {
            this.connectorId = connectorId;
            return this;
        }

        @Override
        public GetMultiTenantedConfigRequest build() {
            return new GetMultiTenantedConfigRequest(this);
        }

    }

}
