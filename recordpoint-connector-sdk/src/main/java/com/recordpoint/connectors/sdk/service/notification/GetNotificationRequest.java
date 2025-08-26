package com.recordpoint.connectors.sdk.service.notification;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.notification.model.Notification;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

public class GetNotificationRequest extends AbstractServiceRequest<Notification> {

    private final String servicePath = "/connector/api/Notifications";

    private final String connectorId;

    private final Boolean receiveAll;

    public GetNotificationRequest(Builder builder) {
        Preconditions.checkNotNull(builder.connectorId, MessageFieldProvider.getMessage("field.connectorId"));
        Preconditions.checkNotNull(builder.receiveAll, MessageFieldProvider.getMessage("field.receiveAll"));
        this.connectorId = builder.connectorId;
        this.receiveAll = builder.receiveAll;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public String getConnectorId() {
        return connectorId;
    }

    public Boolean getReceiveAll() {
        return receiveAll;
    }

    public String getServicePath() {
        return servicePath;
    }

    public static class Builder extends AbstractServiceRequest.Builder {
        String connectorId;
        Boolean receiveAll = false;

        public GetNotificationRequest.Builder setConnectorId(String connectorId) {
            this.connectorId = connectorId;
            return this;
        }

        public GetNotificationRequest.Builder setReceiveAll(Boolean receiveAll) {
            this.receiveAll = receiveAll;
            return this;
        }

        @Override
        public GetNotificationRequest build() {
            return new GetNotificationRequest(this);
        }

    }

}
