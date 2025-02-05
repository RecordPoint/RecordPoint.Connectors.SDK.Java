package com.recordpoint.connectors.sdk.service.aggregation.model;

import com.recordpoint.connectors.sdk.service.ServiceResponse;
import com.recordpoint.connectors.sdk.service.model.Metadata;

import java.time.Instant;
import java.util.List;

public final class Aggregation implements ServiceResponse {

    private String id;

    private String itemType;

    private String itemNumber;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    private String contentSource;

    private Boolean isVitalRecord;

    private List<Metadata> sourceProperties;

    private String externalId;

    private String connectorId;

    private String title;

    private String author;

    private Instant sourceLastModifiedDate;

    private String sourceLastModifiedBy;

    private Instant sourceCreatedDate;

    private String sourceCreatedBy;

    private String location;

    private String mediaType;

    private String parentExternalId;

    private String barcodeType;

    private String barcodeValue;

    private String recordCategoryId;

    private String correlationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getContentSource() {
        return contentSource;
    }

    public void setContentSource(String contentSource) {
        this.contentSource = contentSource;
    }

    public Boolean getVitalRecord() {
        return isVitalRecord;
    }

    public void setVitalRecord(Boolean vitalRecord) {
        isVitalRecord = vitalRecord;
    }

    public List<Metadata> getSourceProperties() {
        return sourceProperties;
    }

    public void setSourceProperties(List<Metadata> sourceProperties) {
        this.sourceProperties = sourceProperties;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Instant getSourceLastModifiedDate() {
        return sourceLastModifiedDate;
    }

    public void setSourceLastModifiedDate(Instant sourceLastModifiedDate) {
        this.sourceLastModifiedDate = sourceLastModifiedDate;
    }

    public String getSourceLastModifiedBy() {
        return sourceLastModifiedBy;
    }

    public void setSourceLastModifiedBy(String sourceLastModifiedBy) {
        this.sourceLastModifiedBy = sourceLastModifiedBy;
    }

    public Instant getSourceCreatedDate() {
        return sourceCreatedDate;
    }

    public void setSourceCreatedDate(Instant sourceCreatedDate) {
        this.sourceCreatedDate = sourceCreatedDate;
    }

    public String getSourceCreatedBy() {
        return sourceCreatedBy;
    }

    public void setSourceCreatedBy(String sourceCreatedBy) {
        this.sourceCreatedBy = sourceCreatedBy;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getParentExternalId() {
        return parentExternalId;
    }

    public void setParentExternalId(String parentExternalId) {
        this.parentExternalId = parentExternalId;
    }

    public String getBarcodeType() {
        return barcodeType;
    }

    public void setBarcodeType(String barcodeType) {
        this.barcodeType = barcodeType;
    }

    public String getBarcodeValue() {
        return barcodeValue;
    }

    public void setBarcodeValue(String barcodeValue) {
        this.barcodeValue = barcodeValue;
    }

    public String getRecordCategoryId() {
        return recordCategoryId;
    }

    public void setRecordCategoryId(String recordCategoryId) {
        this.recordCategoryId = recordCategoryId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
