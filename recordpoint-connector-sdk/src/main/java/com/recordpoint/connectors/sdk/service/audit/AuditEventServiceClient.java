package com.recordpoint.connectors.sdk.service.audit;

import com.recordpoint.connectors.sdk.http.exception.HttpExecutionException;
import com.recordpoint.connectors.sdk.http.exception.HttpResponseException;
import com.recordpoint.connectors.sdk.service.AbstractServiceClient;
import com.recordpoint.connectors.sdk.service.ServiceSettings;

public class AuditEventServiceClient extends AbstractServiceClient {
    public AuditEventServiceClient(Builder builder) {
        super(builder);
    }

    public static AuditEventServiceClient.Builder Builder() {
        return new AuditEventServiceClient.Builder();
    }

    public boolean submitContentSourceEvent(SubmitSourceEventRequest request) throws HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s", getRootUrl(), request.getServicePath());
        putRequest(resourceUrl, request.getPayload());
        return true;
    }

    public static class Builder extends AbstractServiceClient.Builder {

        @Override
        public AuditEventServiceClient build() {
            return new AuditEventServiceClient(this);
        }

        @Override
        public AuditEventServiceClient.Builder setServiceSettings(ServiceSettings serviceSettings) {
            super.setServiceSettings(serviceSettings);
            return this;
        }

    }

}
