package com.recordpoint.connectors.sdk.service.notification.model;

import com.recordpoint.connectors.sdk.service.ServiceResponse;
import com.recordpoint.connectors.sdk.service.model.Metadata;

import java.time.Instant;
import java.util.List;

public class NotificationItem implements ServiceResponse {

    private String id;

    private String itemType;

    private String itemNumber;

    private String format;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    private Instant createdDate;

    private String createdBy;

    private String contentSource;

    private String connectorDisplayName;

    private Boolean isVitalRecord;

    private String originatingOrganization;

    private String previousDisposalAction;

    private Instant previousDisposalDate;

    private String previousDisposalBy;

    private String previousDisposalById;

    private String nextDisposalAction;

    private Instant nextDisposalDate;

    private String currentDisposalStatus;

    private String externalId;

    private String connectorId;

    private String title;

    private String author;

    private String mimeType;

    private Instant sourceLastModifiedDate;

    private String sourceLastModifiedBy;

    private String sourceCreatedBy;

    private Instant sourceCreatedDate;

    private String contentVersion;

    private String location;

    private String mediaType;

    private String parentExternalId;

    private String barcodeType;

    private String barcodeValue;

    private String correlationId;

    private List<Metadata> sourceProperties;

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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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

    public String getContentSource() {
        return contentSource;
    }

    public void setContentSource(String contentSource) {
        this.contentSource = contentSource;
    }

    public String getConnectorDisplayName() {
        return connectorDisplayName;
    }

    public void setConnectorDisplayName(String connectorDisplayName) {
        this.connectorDisplayName = connectorDisplayName;
    }

    public Boolean getVitalRecord() {
        return isVitalRecord;
    }

    public void setVitalRecord(Boolean vitalRecord) {
        isVitalRecord = vitalRecord;
    }

    public String getOriginatingOrganization() {
        return originatingOrganization;
    }

    public void setOriginatingOrganization(String originatingOrganization) {
        this.originatingOrganization = originatingOrganization;
    }

    public String getPreviousDisposalAction() {
        return previousDisposalAction;
    }

    public void setPreviousDisposalAction(String previousDisposalAction) {
        this.previousDisposalAction = previousDisposalAction;
    }

    public Instant getPreviousDisposalDate() {
        return previousDisposalDate;
    }

    public void setPreviousDisposalDate(Instant previousDisposalDate) {
        this.previousDisposalDate = previousDisposalDate;
    }

    public String getPreviousDisposalBy() {
        return previousDisposalBy;
    }

    public void setPreviousDisposalBy(String previousDisposalBy) {
        this.previousDisposalBy = previousDisposalBy;
    }

    public String getPreviousDisposalById() {
        return previousDisposalById;
    }

    public void setPreviousDisposalById(String previousDisposalById) {
        this.previousDisposalById = previousDisposalById;
    }

    public String getNextDisposalAction() {
        return nextDisposalAction;
    }

    public void setNextDisposalAction(String nextDisposalAction) {
        this.nextDisposalAction = nextDisposalAction;
    }

    public Instant getNextDisposalDate() {
        return nextDisposalDate;
    }

    public void setNextDisposalDate(Instant nextDisposalDate) {
        this.nextDisposalDate = nextDisposalDate;
    }

    public String getCurrentDisposalStatus() {
        return currentDisposalStatus;
    }

    public void setCurrentDisposalStatus(String currentDisposalStatus) {
        this.currentDisposalStatus = currentDisposalStatus;
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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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

    public String getSourceCreatedBy() {
        return sourceCreatedBy;
    }

    public void setSourceCreatedBy(String sourceCreatedBy) {
        this.sourceCreatedBy = sourceCreatedBy;
    }

    public Instant getSourceCreatedDate() {
        return sourceCreatedDate;
    }

    public void setSourceCreatedDate(Instant sourceCreatedDate) {
        this.sourceCreatedDate = sourceCreatedDate;
    }

    public String getContentVersion() {
        return contentVersion;
    }

    public void setContentVersion(String contentVersion) {
        this.contentVersion = contentVersion;
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

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public List<Metadata> getSourceProperties() {
        return sourceProperties;
    }

    public void setSourceProperties(List<Metadata> sourceProperties) {
        this.sourceProperties = sourceProperties;
    }
}
