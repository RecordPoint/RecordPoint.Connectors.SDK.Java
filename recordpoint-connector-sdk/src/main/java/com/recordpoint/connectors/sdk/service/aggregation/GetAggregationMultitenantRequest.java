package com.recordpoint.connectors.sdk.service.aggregation;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.aggregation.model.Aggregation;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

import java.util.Objects;

public class GetAggregationMultitenantRequest extends AbstractServiceRequest<Aggregation> {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private final String servicePath = "/connector/api/Aggregations/MultiTenanted";
    private final String fieldName;

    private final String fieldValue;

    private final String connectorId;

    private Integer pageSize = DEFAULT_PAGE_SIZE;

    public GetAggregationMultitenantRequest(Builder builder) {
        Preconditions.checkNotNull(builder.fieldName, MessageFieldProvider.getMessage("field.fieldName"));
        Preconditions.checkNotNull(builder.fieldValue, MessageFieldProvider.getMessage("field.fieldValue"));
        this.fieldName = builder.fieldName;
        this.fieldValue = builder.fieldValue;
        this.pageSize = Objects.isNull(builder.pageSize) ? pageSize : builder.pageSize;
        this.connectorId = builder.connectorId;
    }

    public static GetAggregationMultitenantRequest.Builder Builder() {
        return new GetAggregationMultitenantRequest.Builder();
    }

    public String getServicePath() {
        return servicePath;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public static class Builder extends AbstractServiceRequest.Builder {

        String fieldName;

        String fieldValue;

        Integer pageSize;

        String connectorId;

        public GetAggregationMultitenantRequest.Builder setFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public GetAggregationMultitenantRequest.Builder setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
            return this;
        }

        public GetAggregationMultitenantRequest.Builder setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public GetAggregationMultitenantRequest.Builder setConnectorId(String connectorId) {
            this.connectorId = connectorId;
            return this;
        }

        @Override
        public GetAggregationMultitenantRequest build() {
            return new GetAggregationMultitenantRequest(this);
        }
    }

}
