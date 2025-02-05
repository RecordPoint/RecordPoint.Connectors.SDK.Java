package com.recordpoint.connectors.sdk.service.item.model;

import com.recordpoint.connectors.sdk.service.ServiceResponse;

import java.time.Instant;

public class ItemAcceptance implements ServiceResponse {

    private String externalId;

    private Instant sourceLastModifiedDate;

    private String aggregationStatus;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Instant getSourceLastModifiedDate() {
        return sourceLastModifiedDate;
    }

    public void setSourceLastModifiedDate(Instant sourceLastModifiedDate) {
        this.sourceLastModifiedDate = sourceLastModifiedDate;
    }

    public String getAggregationStatus() {
        return aggregationStatus;
    }

    public void setAggregationStatus(String aggregationStatus) {
        this.aggregationStatus = aggregationStatus;
    }
}
