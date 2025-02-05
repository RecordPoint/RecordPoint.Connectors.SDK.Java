package com.recordpoint.connectors.sdk.service.notification;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.binary.model.EmptyResponse;
import com.recordpoint.connectors.sdk.service.notification.model.NotificationAcknowledge;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

public class AcknowledgeNotificationRequest extends AbstractServiceRequest<EmptyResponse> {

    private final String servicePath = "/connector/api/Notifications";

    private final NotificationAcknowledge payload;

    public AcknowledgeNotificationRequest(Builder builder) {
        Preconditions.checkNotNull(builder.payload, MessageFieldProvider.getMessage("field.payload"));
        this.payload = builder.payload;
    }

    public static AcknowledgeNotificationRequest.Builder Builder() {
        return new AcknowledgeNotificationRequest.Builder();
    }

    public String getServicePath() {
        return servicePath;
    }

    public NotificationAcknowledge getPayload() {
        return payload;
    }

    public static class Builder extends AbstractServiceRequest.Builder {
        NotificationAcknowledge payload;

        public AcknowledgeNotificationRequest.Builder setNotificationAcknowledge(NotificationAcknowledge payload) {
            this.payload = payload;
            return this;
        }

        @Override
        public AcknowledgeNotificationRequest build() {
            return new AcknowledgeNotificationRequest(this);
        }

    }

}
