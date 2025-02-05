package com.recordpoint.connectors.sdk.service.binary;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.binary.model.DirectBinarySubmission;
import com.recordpoint.connectors.sdk.service.binary.model.DirectBinarySubmissionOutput;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

public class GetBlobTokenResourceRequest extends AbstractServiceRequest<DirectBinarySubmissionOutput> {

    private final String servicePath = "/connector/api/Binaries/GetSASToken";

    private final DirectBinarySubmission payload;

    public GetBlobTokenResourceRequest(Builder builder) {
        Preconditions.checkNotNull(builder.payload, MessageFieldProvider.getMessage("field.payload"));
        this.payload = builder.payload;
    }

    public static GetBlobTokenResourceRequest.Builder Builder() {
        return new GetBlobTokenResourceRequest.Builder();
    }

    public String getServicePath() {
        return servicePath;
    }

    public DirectBinarySubmission getPayload() {
        return payload;
    }

    public static class Builder extends AbstractServiceRequest.Builder {

        private DirectBinarySubmission payload;

        public GetBlobTokenResourceRequest.Builder setPayload(DirectBinarySubmission payload) {
            this.payload = payload;
            return this;
        }

        @Override
        public GetBlobTokenResourceRequest build() {
            return new GetBlobTokenResourceRequest(this);
        }

    }

}
