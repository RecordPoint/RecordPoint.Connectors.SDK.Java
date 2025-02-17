package com.recordpoint.connectors.sdk.service.notification.model;

import com.recordpoint.connectors.sdk.service.ServicePayload;

public class NotificationAcknowledge implements ServicePayload {
    public enum ProcessingResult {
        /**
         * Indicates that the required processing for the notification completed successfully.
         */
        OK("OK"),

        /**
         * Indicates that the result of processing a notification is unknown.
         */
        UNKNOWN("Unknown"),

        /**
         * Indicates that the notification was not processed because the connector is not enabled.
         */
        CONNECTOR_DISABLED("ConnectorDisabled"),

        /**
         * Indicates that the notification was not processed because the connector is not subscribed
         * to the notification type
         */
        CONNECTOR_NOT_SUBSCRIBED("ConnectorNotSubscribed"),
        /**
         * Indicates that the notification was not processed because the connector could not be reached.
         */
        CONNECTOR_NOT_REACHABLE("ConnectorNotReachable"),

        /**
         * Indicates that an error occurred during processing of a notification.
         */
        NOTIFICATION_ERROR("NotificationError");

        private final String value;

        ProcessingResult(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static ProcessingResult fromValue(String value) {
            for (ProcessingResult result : values()) {
                if (result.value.equals(value)) {
                    return result;
                }
            }

            return null;
        }
    }


    private String connectorId;
    private String notificationId;
    private ProcessingResult processingResult;
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

    public ProcessingResult getProcessingResult() {
        return processingResult;
    }

    public void setProcessingResult(String processingResult) {
        this.processingResult = ProcessingResult.fromValue(processingResult);
    }

    public void setProcessingResult(ProcessingResult processingResult) {
        this.processingResult = processingResult;
    }

    public String getConnectorStatusMessage() {
        return connectorStatusMessage;
    }

    public void setConnectorStatusMessage(String connectorStatusMessage) {
        this.connectorStatusMessage = connectorStatusMessage;
    }

    public NotificationAcknowledge(String connectorId, String notificationId, ProcessingResult processingResult, String connectorStatusMessage) {
        this.connectorId = connectorId;
        this.notificationId = notificationId;
        this.processingResult = processingResult;
        this.connectorStatusMessage = connectorStatusMessage;
    }

    public NotificationAcknowledge() {
    }

    public static NotificationAcknowledge.Builder Builder() {
        return new Builder();
    }

    public static class Builder {
        private String connectorId;
        private String notificationId;
        private ProcessingResult processingResult;
        private String connectorStatusMessage;

        public Builder setConnectorId(String connectorId) {
            this.connectorId = connectorId;
            return this;
        }

        public Builder setNotificationId(String notificationId) {
            this.notificationId = notificationId;
            return this;
        }

        public Builder setProcessingResult(ProcessingResult processingResult) {
            this.processingResult = processingResult;
            return this;
        }

        public Builder setConnectorStatusMessage(String connectorStatusMessage) {
            this.connectorStatusMessage = connectorStatusMessage;
            return this;
        }

        public NotificationAcknowledge build() {
            return new NotificationAcknowledge(
                   connectorId, notificationId, processingResult, connectorStatusMessage
            );
        }
    }
}
