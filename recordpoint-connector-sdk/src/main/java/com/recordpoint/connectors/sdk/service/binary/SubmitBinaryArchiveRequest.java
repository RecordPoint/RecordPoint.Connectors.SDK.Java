package com.recordpoint.connectors.sdk.service.binary;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.binary.model.EmptyResponse;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

public class SubmitBinaryArchiveRequest extends AbstractServiceRequest<EmptyResponse> {

    private final String servicePath = "/connector/api/Binaries";

    private final String connectorId;

    private final String itemExternalId;

    private final String binaryExternalId;

    public SubmitBinaryArchiveRequest(Builder builder) {
        Preconditions.checkNotNull(builder.connectorId, MessageFieldProvider.getMessage("field.connectorId"));
        Preconditions.checkNotNull(builder.itemExternalId, MessageFieldProvider.getMessage("field.itemExternalId"));
        Preconditions.checkNotNull(builder.binaryExternalId, MessageFieldProvider.getMessage("field.binaryExternalId"));
        this.binaryExternalId = builder.binaryExternalId;
        this.itemExternalId = builder.itemExternalId;
        this.connectorId = builder.connectorId;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public String getServicePath() {
        return servicePath;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public String getItemExternalId() {
        return itemExternalId;
    }

    public String getBinaryExternalId() {
        return binaryExternalId;
    }

    public static class Builder extends AbstractServiceRequest.Builder {

        private String connectorId;

        private String itemExternalId;

        private String binaryExternalId;

        public Builder setConnectorId(String connectorId) {
            this.connectorId = connectorId;
            return this;
        }

        public Builder setCItemExternalId(String itemExternalId) {
            this.itemExternalId = itemExternalId;
            return this;
        }

        public Builder setBinaryExternalId(String binaryExternalId) {
            this.binaryExternalId = binaryExternalId;
            return this;
        }

        @Override
        public SubmitBinaryArchiveRequest build() {
            return new SubmitBinaryArchiveRequest(this);
        }

    }

}
