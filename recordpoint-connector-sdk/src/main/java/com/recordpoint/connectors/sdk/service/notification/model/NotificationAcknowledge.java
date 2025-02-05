package com.recordpoint.connectors.sdk.service.notification.model;

import com.recordpoint.connectors.sdk.service.ServicePayload;

public class NotificationAcknowledge implements ServicePayload {

    private String connectorId;
    private String notificationId;
    private String processingResult;
    private String connectorStatusMessage;

    public String getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getProcessingResult() {
        return processingResult;
    }

    public void setProcessingResult(String processingResult) {
        this.processingResult = processingResult;
    }

    public String getConnectorStatusMessage() {
        return connectorStatusMessage;
    }

    public void setConnectorStatusMessage(String connectorStatusMessage) {
        this.connectorStatusMessage = connectorStatusMessage;
    }
}
