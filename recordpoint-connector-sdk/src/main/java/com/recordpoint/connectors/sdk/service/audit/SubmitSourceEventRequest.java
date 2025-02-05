package com.recordpoint.connectors.sdk.service.audit;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.audit.model.AuditEvent;
import com.recordpoint.connectors.sdk.service.binary.model.EmptyResponse;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

public class SubmitSourceEventRequest extends AbstractServiceRequest<EmptyResponse> {

    private final String servicePath = "/connector/api/AuditEvents";

    private final AuditEvent payload;

    public SubmitSourceEventRequest(Builder builder) {
        Preconditions.checkNotNull(builder.payload, MessageFieldProvider.getMessage("field.payload"));
        this.payload = builder.payload;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public String getServicePath() {
        return servicePath;
    }

    public AuditEvent getPayload() {
        return payload;
    }

    public static class Builder extends AbstractServiceRequest.Builder {
        AuditEvent payload;

        public SubmitSourceEventRequest.Builder setAuditEvent(AuditEvent payload) {
            this.payload = payload;
            return this;
        }

        @Override
        public SubmitSourceEventRequest build() {
            return new SubmitSourceEventRequest(this);
        }

    }

}
