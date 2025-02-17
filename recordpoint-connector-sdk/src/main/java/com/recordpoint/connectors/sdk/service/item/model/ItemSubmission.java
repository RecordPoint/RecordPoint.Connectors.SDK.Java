package com.recordpoint.connectors.sdk.service.item.model;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.ServicePayload;
import com.recordpoint.connectors.sdk.service.binary.model.DirectBinarySubmission;
import com.recordpoint.connectors.sdk.service.model.Metadata;
import com.recordpoint.connectors.sdk.service.model.RelationshipData;
import com.recordpoint.connectors.sdk.service.notification.model.Notification;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

import java.time.Instant;
import java.util.List;

public class ItemSubmission implements ServicePayload {

    public enum MediaType {
        ELECTRONIC("Electronic"),
        PHYSICAL("Physical");

        private final String value;

        MediaType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static MediaType fromValue(String value) {
            for (MediaType mt : values()) {
                if (mt.value.equals(value)) {
                    return mt;
                }
            }

            return null;
        }
    }

    private final String securityProfileIdentifier;
    private final List<Metadata> sourceProperties;
    private final List<RelationshipData> relationships;
    private final List<DirectBinarySubmission> binariesSubmitted;
    private final String externalId;
    private final String connectorId;
    private final String title;
    private final String author;
    private final Instant sourceLastModifiedDate;
    private final String sourceLastModifiedBy;
    private final Instant sourceCreatedDate;
    private final String sourceCreatedBy;
    private final String location;
    private final String parentExternalId;
    private final String mimeType;
    private final String contentVersion;
    private final String mediaType;
    private final String barcodeType;
    private final String barcodeValue;
    private final String correlationId;

    public ItemSubmission(Builder builder) {
        Preconditions.checkNotNull(builder.author, MessageFieldProvider.getMessage("field.author"));
        Preconditions.checkNotNull(builder.connectorId, MessageFieldProvider.getMessage("field.connectorId"));
        Preconditions.checkNotNull(builder.contentVersion, MessageFieldProvider.getMessage("field.contentVersion"));
        Preconditions.checkNotNull(builder.externalId, MessageFieldProvider.getMessage("field.externalId"));
        Preconditions.checkNotNull(builder.location, MessageFieldProvider.getMessage("field.location"));
        Preconditions.checkNotNull(builder.mediaType, MessageFieldProvider.getMessage("field.mediaType"));
        Preconditions.checkNotNull(builder.parentExternalId, MessageFieldProvider.getMessage("field.parentExternalId"));
        Preconditions.checkNotNull(builder.sourceCreatedBy, MessageFieldProvider.getMessage("field.sourceCreatedBy"));
        Preconditions.checkNotNull(builder.sourceCreatedDate, MessageFieldProvider.getMessage("field.sourceCreatedDate"));
        Preconditions.checkNotNull(builder.sourceLastModifiedBy, MessageFieldProvider.getMessage("field.sourceLastModifiedBy"));
        Preconditions.checkNotNull(builder.sourceLastModifiedDate, MessageFieldProvider.getMessage("field.sourceLastModifiedDate"));
        Preconditions.checkNotNull(builder.title, MessageFieldProvider.getMessage("field.title"));
        this.securityProfileIdentifier = builder.securityProfileIdentifier;
        this.sourceProperties = builder.sourceProperties;
        this.relationships = builder.relationships;
        this.binariesSubmitted = builder.binariesSubmitted;
        this.author = builder.author;
        this.connectorId = builder.connectorId;
        this.externalId = builder.externalId;
        this.location = builder.location;
        this.parentExternalId = builder.parentExternalId;
        this.sourceCreatedBy = builder.sourceCreatedBy;
        this.sourceCreatedDate = builder.sourceCreatedDate;
        this.sourceLastModifiedBy = builder.sourceLastModifiedBy;
        this.title = builder.title;
        this.sourceLastModifiedDate = builder.sourceLastModifiedDate;
        this.mimeType = builder.mimeType;
        this.contentVersion = builder.contentVersion;
        this.mediaType = builder.mediaType;
        this.barcodeType = builder.barcodeType;
        this.barcodeValue = builder.barcodeValue;
        this.correlationId = builder.correlationId;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public String getExternalId() {
        return externalId;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Instant getSourceLastModifiedDate() {
        return sourceLastModifiedDate;
    }

    public String getSourceLastModifiedBy() {
        return sourceLastModifiedBy;
    }

    public Instant getSourceCreatedDate() {
        return sourceCreatedDate;
    }

    public String getSourceCreatedBy() {
        return sourceCreatedBy;
    }

    public String getLocation() {
        return location;
    }

    public String getParentExternalId() {
        return parentExternalId;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getContentVersion() {
        return contentVersion;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getSecurityProfileIdentifier() {
        return securityProfileIdentifier;
    }

    public List<Metadata> getSourceProperties() {
        return sourceProperties;
    }

    public List<RelationshipData> getRelationships() {
        return relationships;
    }

    public List<DirectBinarySubmission> getBinariesSubmitted() {
        return binariesSubmitted;
    }

    public String getBarcodeType() {
        return barcodeType;
    }

    public String getBarcodeValue() {
        return barcodeValue;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public static class Builder {
        private String securityProfileIdentifier;
        private List<Metadata> sourceProperties;
        private List<RelationshipData> relationships;
        private List<DirectBinarySubmission> binariesSubmitted;
        private String externalId;
        private String connectorId;
        private String title;
        private Instant sourceLastModifiedDate;
        private String sourceLastModifiedBy;
        private Instant sourceCreatedDate;
        private String sourceCreatedBy;
        private String author;
        private String location;
        private String parentExternalId;
        private String mimeType;
        private String contentVersion;
        private String mediaType;
        private String barcodeType;
        private String barcodeValue;
        private String correlationId;


        public Builder setExternalId(String externalId) {
            this.externalId = externalId;
            return this;
        }

        public Builder setConnectorId(String connectorId) {
            this.connectorId = connectorId;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSourceLastModifiedDate(Instant sourceLastModifiedDate) {
            this.sourceLastModifiedDate = sourceLastModifiedDate;
            return this;
        }

        public Builder setSourceLastModifiedBy(String sourceLastModifiedBy) {
            this.sourceLastModifiedBy = sourceLastModifiedBy;
            return this;
        }

        public Builder setSourceCreatedDate(Instant sourceCreatedDate) {
            this.sourceCreatedDate = sourceCreatedDate;
            return this;
        }

        public Builder setSourceCreatedBy(String sourceCreatedBy) {
            this.sourceCreatedBy = sourceCreatedBy;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setParentExternalId(String parentExternalId) {
            this.parentExternalId = parentExternalId;
            return this;
        }

        public Builder setMimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public Builder setContentVersion(String contentVersion) {
            this.contentVersion = contentVersion;
            return this;
        }

        public Builder setMediaType(String mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public Builder setMediaType(MediaType mediaType) {
            this.mediaType = mediaType.toString();
            return this;
        }

        public Builder setSecurityProfileIdentifier(String securityProfileIdentifier) {
            this.securityProfileIdentifier = securityProfileIdentifier;
            return this;
        }

        public Builder setSourceProperties(List<Metadata> sourceProperties) {
            this.sourceProperties = sourceProperties;
            return this;
        }

        public Builder setRelationships(List<RelationshipData> relationships) {
            this.relationships = relationships;
            return this;
        }

        public Builder setBinariesSubmitted(List<DirectBinarySubmission> binariesSubmitted) {
            this.binariesSubmitted = binariesSubmitted;
            return this;
        }

        public Builder setBarcodeType(String barcodeType) {
            this.barcodeType = barcodeType;
            return this;
        }

        public Builder setBarcodeValue(String barcodeValue) {
            this.barcodeValue = barcodeValue;
            return this;
        }

        public Builder setCorrelationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public ItemSubmission build() {
            return new ItemSubmission(this);
        }
    }

}
