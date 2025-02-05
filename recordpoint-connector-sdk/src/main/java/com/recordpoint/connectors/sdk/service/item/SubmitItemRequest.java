package com.recordpoint.connectors.sdk.service.item;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.item.model.ItemAcceptance;
import com.recordpoint.connectors.sdk.service.item.model.ItemSubmission;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

public final class SubmitItemRequest extends AbstractServiceRequest<ItemAcceptance> {

    private final String servicePath = "/connector/api/Items";

    private final ItemSubmission submission;

    public SubmitItemRequest(Builder builder) {
        Preconditions.checkNotNull(builder.payload, MessageFieldProvider.getMessage("field.payload"));
        this.submission = builder.payload;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public String getServicePath() {
        return servicePath;
    }

    public ItemSubmission getSubmission() {
        return submission;
    }

    public static class Builder extends AbstractServiceRequest.Builder {

        ItemSubmission payload;

        public Builder setPayload(ItemSubmission submission) {
            this.payload = submission;
            return this;
        }

        @Override
        public SubmitItemRequest build() {
            return new SubmitItemRequest(this);
        }
    }


}
