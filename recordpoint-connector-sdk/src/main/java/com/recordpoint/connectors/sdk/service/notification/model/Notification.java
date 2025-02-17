package com.recordpoint.connectors.sdk.service.notification.model;

import com.recordpoint.connectors.sdk.service.ServiceResponse;
import com.recordpoint.connectors.sdk.service.model.ConnectorConfig;

import java.time.Instant;

public class Notification implements ServiceResponse {
    public enum NotificationType {
        PING("Ping"),
        ITEM_DESTROYED("ItemDestroyed"),
        CONNECTOR_CONFIG_CREATED("ConnectorConfigCreated"),
        CONNECTOR_CONFIG_DELETED("ConnectorConfigDeleted"),
        CONNECTOR_CONFIG_UPDATED("ConnectorConfigUpdated");

        private final String value;

        NotificationType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static NotificationType fromValue(String value) {
            for (NotificationType notificationType : NotificationType.values()) {
                if (notificationType.value.equals(value)) {
                    return notificationType;
                }
            }

            return null;
        }
    }

    private String id;

    private NotificationType notificationType;

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

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = NotificationType.fromValue(notificationType);
    }

    public void setNotificationType(NotificationType notificationType) {
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
