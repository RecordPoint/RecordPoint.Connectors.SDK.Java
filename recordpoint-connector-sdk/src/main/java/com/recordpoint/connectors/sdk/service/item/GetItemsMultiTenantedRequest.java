package com.recordpoint.connectors.sdk.service.item;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.AbstractServiceRequest;
import com.recordpoint.connectors.sdk.service.item.model.ItemAcceptance;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

import java.util.Objects;

public class GetItemsMultiTenantedRequest extends AbstractServiceRequest<ItemAcceptance> {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private final String servicePath = "/connector/api/Items";
    private final String fieldName;

    private final String fieldValue;

    private final String connectorId;

    private Integer pageSize = DEFAULT_PAGE_SIZE;

    public GetItemsMultiTenantedRequest(Builder builder) {
        Preconditions.checkNotNull(builder.fieldName, MessageFieldProvider.getMessage("field.fieldName"));
        Preconditions.checkNotNull(builder.fieldValue, MessageFieldProvider.getMessage("field.fieldValue"));
        this.fieldName = builder.fieldName;
        this.fieldValue = builder.fieldValue;
        this.pageSize = Objects.isNull(builder.pageSize) ? pageSize : builder.pageSize;
        this.connectorId = builder.connectorId;
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

        public GetItemsMultiTenantedRequest.Builder setFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public GetItemsMultiTenantedRequest.Builder setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
            return this;
        }

        public GetItemsMultiTenantedRequest.Builder setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public GetItemsMultiTenantedRequest.Builder setConnectorId(String connectorId) {
            this.connectorId = connectorId;
            return this;
        }

        public GetItemsMultiTenantedRequest build() {
            return new GetItemsMultiTenantedRequest(this);
        }

    }

}
