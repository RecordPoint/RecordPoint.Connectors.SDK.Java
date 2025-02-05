package com.recordpoint.connectors.sdk.service.audit.model;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.ServicePayload;
import com.recordpoint.connectors.sdk.service.model.Metadata;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

import java.time.Instant;
import java.util.List;

public class AuditEvent implements ServicePayload {

    private final String eventExternalId;
    private final String connectorId;
    private final Instant createdDate;
    private final String eventType;
    private final String description;
    private final String userId;
    private final String userName;
    private final String itemExternalId;
    private final List<Metadata> sourceProperties;

    public AuditEvent(Builder builder) {
        Preconditions.checkNotNull(builder.connectorId, MessageFieldProvider.getMessage("field.connectorId"));
        Preconditions.checkNotNull(builder.eventExternalId, MessageFieldProvider.getMessage("field.eventExternalId"));
        Preconditions.checkNotNull(builder.eventType, MessageFieldProvider.getMessage("field.eventType"));
        Preconditions.checkNotNull(builder.itemExternalId, MessageFieldProvider.getMessage("field.itemExternalId"));
        this.eventExternalId = builder.eventExternalId;
        this.connectorId = builder.connectorId;
        this.createdDate = builder.createdDate;
        this.eventType = builder.eventType;
        this.description = builder.description;
        this.userId = builder.userId;
        this.userName = builder.userName;
        this.itemExternalId = builder.itemExternalId;
        this.sourceProperties = builder.sourceProperties;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public String getEventExternalId() {
        return eventExternalId;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public String getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getItemExternalId() {
        return itemExternalId;
    }

    public List<Metadata> getSourceProperties() {
        return sourceProperties;
    }

    public static class Builder {
        private String eventExternalId;
        private String connectorId;
        private Instant createdDate;
        private String eventType;
        private String description;
        private String userId;
        private String userName;
        private String itemExternalId;
        private List<Metadata> sourceProperties;

        public Builder eventExternalId(String eventExternalId) {
            this.eventExternalId = eventExternalId;
            return this;
        }

        public Builder connectorId(String connectorId) {
            this.connectorId = connectorId;
            return this;
        }

        public Builder createdDate(Instant createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder eventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder itemExternalId(String itemExternalId) {
            this.itemExternalId = itemExternalId;
            return this;
        }

        public Builder sourceProperties(List<Metadata> sourceProperties) {
            this.sourceProperties = sourceProperties;
            return this;
        }

        public AuditEvent build() {
            return new AuditEvent(this);
        }

    }

}
