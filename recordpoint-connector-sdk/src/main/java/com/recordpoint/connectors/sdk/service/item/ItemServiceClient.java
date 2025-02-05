package com.recordpoint.connectors.sdk.service.item;

import com.recordpoint.connectors.sdk.http.exception.HttpExecutionException;
import com.recordpoint.connectors.sdk.http.exception.HttpResponseException;
import com.recordpoint.connectors.sdk.json.JsonMapperException;
import com.recordpoint.connectors.sdk.service.AbstractServiceClient;
import com.recordpoint.connectors.sdk.service.ServiceSettings;
import com.recordpoint.connectors.sdk.service.item.model.ItemAcceptance;
import com.recordpoint.connectors.sdk.service.item.model.ItemSubmissionOutput;

import java.util.List;

public final class ItemServiceClient extends AbstractServiceClient {

    public ItemServiceClient(Builder builder) {
        super(builder);
    }

    public static Builder Builder() {
        return new Builder();
    }

    public ItemAcceptance submitItem(SubmitItemRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s", getRootUrl(), request.getServicePath());
        return postRequest(resourceUrl, request.getSubmission(), ItemAcceptance.class);
    }

    public List<ItemSubmissionOutput> getItemsList(GetItemsRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s/%s/%s?pagesize=%s", getRootUrl(), request.getServicePath(),
                request.getFieldName(), request.getFieldValue(), request.getPageSize());
        return getRequestList(resourceUrl, ItemSubmissionOutput.class);
    }

    public List<ItemSubmissionOutput> getItemsMultiTenantedList(GetItemsMultiTenantedRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s/%s/%s?connectorId=%s", getRootUrl(), request.getServicePath(),
                request.getFieldName(), request.getFieldValue(), request.getConnectorId());
        return getRequestList(resourceUrl, ItemSubmissionOutput.class);
    }

    public static class Builder extends AbstractServiceClient.Builder {

        @Override
        public ItemServiceClient build() {
            return new ItemServiceClient(this);
        }

        @Override
        public ItemServiceClient.Builder setServiceSettings(ServiceSettings serviceSettings) {
            super.setServiceSettings(serviceSettings);
            return this;
        }

    }

}
