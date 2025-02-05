package com.recordpoint.connectors.sdk.service.notification.model;

import com.recordpoint.connectors.sdk.service.ServiceResponse;
import com.recordpoint.connectors.sdk.service.model.ConnectorConfig;

import java.time.Instant;

public class Notification implements ServiceResponse {

    private String id;

    private String notificationType;

    private Instant timestamp;

    private String tenantId;

    private String connectorId;

    private String context;

    private NotificationItem item;

    private ConnectorConfig connectorConfig;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public NotificationItem getItem() {
        return item;
    }

    public void setItem(NotificationItem item) {
        this.item = item;
    }

    public ConnectorConfig getConnectorConfig() {
        return connectorConfig;
    }

    public void setConnectorConfig(ConnectorConfig connectorConfig) {
        this.connectorConfig = connectorConfig;
    }
}
