package com.recordpoint.connectors.sdk.service.connector;

import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.model.ConnectorConfig;

public class GetConnectorConfigRequest extends AbstractServiceRequest<ConnectorConfig> {

    private final String servicePath = "/connector/api/ConnectorConfigurations";

    private final String id;

    public GetConnectorConfigRequest(Builder builder) {
        this.id = builder.id;
    }

    public static GetConnectorConfigRequest.Builder Builder() {
        return new GetConnectorConfigRequest.Builder();
    }

    public String getServicePath() {
        return servicePath;
    }

    public String getId() {
        return id;
    }

    public static class Builder extends AbstractServiceRequest.Builder {
        String id;

        public GetConnectorConfigRequest.Builder setId(String id) {
            this.id = id;
            return this;
        }

        @Override
        public GetConnectorConfigRequest build() {
            return new GetConnectorConfigRequest(this);
        }

    }


}
