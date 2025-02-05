package com.recordpoint.connectors.sdk.service.aggregation;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.aggregation.model.Aggregation;
import com.recordpoint.connectors.sdk.service.aggregation.model.AggregationSubmission;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

public final class SubmitAggregationRequest extends AbstractServiceRequest<Aggregation> {

    private final String servicePath = "/connector/api/Aggregations";

    private final AggregationSubmission submission;

    public SubmitAggregationRequest(Builder builder) {
        Preconditions.checkNotNull(builder.payload, MessageFieldProvider.getMessage("field.payload"));
        this.submission = builder.payload;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public AggregationSubmission getSubmission() {
        return submission;
    }

    public String getServicePath() {
        return servicePath;
    }

    public static class Builder extends AbstractServiceRequest.Builder {

        AggregationSubmission payload;

        public Builder setPayload(AggregationSubmission submission) {
            this.payload = submission;
            return this;
        }

        @Override
        public SubmitAggregationRequest build() {
            return new SubmitAggregationRequest(this);
        }
    }


}
