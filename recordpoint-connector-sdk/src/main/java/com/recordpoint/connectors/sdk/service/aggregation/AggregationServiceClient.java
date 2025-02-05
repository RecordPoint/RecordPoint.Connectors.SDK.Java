package com.recordpoint.connectors.sdk.service.aggregation;

import com.recordpoint.connectors.sdk.http.exception.HttpExecutionException;
import com.recordpoint.connectors.sdk.http.exception.HttpResponseException;
import com.recordpoint.connectors.sdk.json.JsonMapperException;
import com.recordpoint.connectors.sdk.service.AbstractServiceClient;
import com.recordpoint.connectors.sdk.service.ServiceSettings;
import com.recordpoint.connectors.sdk.service.aggregation.model.Aggregation;

import java.util.List;

public final class AggregationServiceClient extends AbstractServiceClient {

    public AggregationServiceClient(Builder builder) {
        super(builder);
    }

    public static Builder Builder() {
        return new Builder();
    }

    public List<Aggregation> getAggregationList(GetAggregationRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s/%s/%s", getRootUrl(), request.getServicePath(),
                request.getFieldName(), request.getFieldValue());
        return getRequestList(resourceUrl, Aggregation.class);
    }

    public boolean submitAggregation(SubmitAggregationRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s", getRootUrl(), request.getServicePath());
        postRequest(resourceUrl, request.getSubmission());
        return true;
    }

    public List<Aggregation> getAggregationMultiTenanted(GetAggregationMultitenantRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s/%s/%s?connectorId=%s", getRootUrl(), request.getServicePath(),
                request.getFieldName(), request.getFieldValue(), request.getConnectorId());
        return getRequestList(resourceUrl, Aggregation.class);
    }

    public static class Builder extends AbstractServiceClient.Builder {

        @Override
        public AggregationServiceClient build() {
            return new AggregationServiceClient(this);
        }

        @Override
        public AggregationServiceClient.Builder setServiceSettings(ServiceSettings serviceSettings) {
            super.setServiceSettings(serviceSettings);
            return this;
        }
    }

}
