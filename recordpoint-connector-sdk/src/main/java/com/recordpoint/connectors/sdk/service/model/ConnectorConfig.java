package com.recordpoint.connectors.sdk.service.model;

import com.recordpoint.connectors.sdk.service.ServiceResponse;
import com.recordpoint.connectors.sdk.service.notification.model.NotificationFilter;

import java.time.Instant;
import java.util.List;

public class ConnectorConfig implements ServiceResponse {

    private String id;

    private String transactionId;

    private String connectorTypeId;

    private String connectorTypeConfigurationId;

    private String status;

    private String statusCode;

    private Instant createdDate;

    private Instant modifiedDate;

    private String createdBy;

    private String modifiedBy;

    private String tenantId;

    private String tenantDomainName;

    private String originatingOrganization;

    private String enabledHistory;

    private String displayName;

    private Boolean hasSubmittedData;

    private List<Metadata> properties;

    private String clientId;

    private String protectionEnabled;

    private NotificationFilter filters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getConnectorTypeId() {
        return connectorTypeId;
    }

    public void setConnectorTypeId(String connectorTypeId) {
        this.connectorTypeId = connectorTypeId;
    }

    public String getConnectorTypeConfigurationId() {
        return connectorTypeConfigurationId;
    }

    public void setConnectorTypeConfigurationId(String connectorTypeConfigurationId) {
        this.connectorTypeConfigurationId = connectorTypeConfigurationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantDomainName() {
        return tenantDomainName;
    }

    public void setTenantDomainName(String tenantDomainName) {
        this.tenantDomainName = tenantDomainName;
    }

    public String getOriginatingOrganization() {
        return originatingOrganization;
    }

    public void setOriginatingOrganization(String originatingOrganization) {
        this.originatingOrganization = originatingOrganization;
    }

    public String getEnabledHistory() {
        return enabledHistory;
    }

    public void setEnabledHistory(String enabledHistory) {
        this.enabledHistory = enabledHistory;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getHasSubmittedData() {
        return hasSubmittedData;
    }

    public void setHasSubmittedData(Boolean hasSubmittedData) {
        this.hasSubmittedData = hasSubmittedData;
    }

    public List<Metadata> getProperties() {
        return properties;
    }

    public void setProperties(List<Metadata> properties) {
        this.properties = properties;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getProtectionEnabled() {
        return protectionEnabled;
    }

    public void setProtectionEnabled(String protectionEnabled) {
        this.protectionEnabled = protectionEnabled;
    }

    public NotificationFilter getFilters() {
        return filters;
    }

    public void setFilters(NotificationFilter filters) {
        this.filters = filters;
    }
}
