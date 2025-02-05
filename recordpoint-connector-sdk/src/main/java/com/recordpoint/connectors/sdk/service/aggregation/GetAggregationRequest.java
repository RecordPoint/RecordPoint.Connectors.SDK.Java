package com.recordpoint.connectors.sdk.service.aggregation;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.aggregation.model.Aggregation;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

import java.util.Objects;

public final class GetAggregationRequest extends AbstractServiceRequest<Aggregation> {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private final String servicePath = "/connector/api/Aggregations";
    private final String fieldName;

    private final String fieldValue;

    private Integer pageSize = DEFAULT_PAGE_SIZE;

    public GetAggregationRequest(Builder builder) {
        Preconditions.checkNotNull(builder.fieldName, MessageFieldProvider.getMessage("field.fieldName"));
        Preconditions.checkNotNull(builder.fieldValue, MessageFieldProvider.getMessage("field.fieldValue"));
        this.fieldName = builder.fieldName;
        this.fieldValue = builder.fieldValue;
        this.pageSize = Objects.isNull(builder.pageSize) ? pageSize : builder.pageSize;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getServicePath() {
        return servicePath;
    }

    public static class Builder extends AbstractServiceRequest.Builder {

        String fieldName;

        String fieldValue;

        Integer pageSize;

        public GetAggregationRequest.Builder setFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public GetAggregationRequest.Builder setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
            return this;
        }

        public GetAggregationRequest.Builder setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        @Override
        public GetAggregationRequest build() {
            return new GetAggregationRequest(this);
        }
    }

}
