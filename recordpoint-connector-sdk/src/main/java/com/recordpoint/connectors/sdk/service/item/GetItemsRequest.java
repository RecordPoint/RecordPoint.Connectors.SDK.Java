package com.recordpoint.connectors.sdk.service.item;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.item.model.ItemAcceptance;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

import java.util.Objects;

public class GetItemsRequest extends AbstractServiceRequest<ItemAcceptance> {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private final String servicePath = "/connector/api/Items";
    private final String fieldName;

    private final String fieldValue;

    private Integer pageSize = DEFAULT_PAGE_SIZE;

    public GetItemsRequest(Builder builder) {
        Preconditions.checkNotNull(builder.fieldName, MessageFieldProvider.getMessage("field.fieldName"));
        Preconditions.checkNotNull(builder.fieldValue, MessageFieldProvider.getMessage("field.fieldValue"));
        this.fieldName = builder.fieldName;
        this.fieldValue = builder.fieldValue;
        this.pageSize = Objects.isNull(builder.pageSize) ? pageSize : builder.pageSize;
    }

    public static Builder Builder() {
        return new Builder();
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

    public Integer getPageSize() {
        return pageSize;
    }

    public static class Builder extends AbstractServiceRequest.Builder {

        String fieldName;

        String fieldValue;

        Integer pageSize;

        public GetItemsRequest.Builder setFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public GetItemsRequest.Builder setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
            return this;
        }

        public GetItemsRequest.Builder setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        @Override
        public GetItemsRequest build() {
            return new GetItemsRequest(this);
        }

    }

}
