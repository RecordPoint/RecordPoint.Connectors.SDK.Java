package com.recordpoint.connectors.sdk.service.binary;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.binary.model.DirectBinarySubmission;
import com.recordpoint.connectors.sdk.service.binary.model.EmptyResponse;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

public class NotifiesNewBinaryUploadedRequest extends AbstractServiceRequest<EmptyResponse> {

    private final String servicePath = "/connector/api/Binaries/NotifyBinarySubmission";

    private final DirectBinarySubmission payload;

    public NotifiesNewBinaryUploadedRequest(Builder builder) {
        Preconditions.checkNotNull(builder.payload, MessageFieldProvider.getMessage("field.payload"));
        this.payload = builder.payload;
    }

    public static NotifiesNewBinaryUploadedRequest.Builder Builder() {
        return new NotifiesNewBinaryUploadedRequest.Builder();
    }

    public String getServicePath() {
        return servicePath;
    }

    public DirectBinarySubmission getPayload() {
        return payload;
    }

    public static class Builder extends AbstractServiceRequest.Builder {

        private DirectBinarySubmission payload;

        public NotifiesNewBinaryUploadedRequest.Builder setPayload(DirectBinarySubmission payload) {
            this.payload = payload;
            return this;
        }

        @Override
        public NotifiesNewBinaryUploadedRequest build() {
            return new NotifiesNewBinaryUploadedRequest(this);
        }

    }

}
