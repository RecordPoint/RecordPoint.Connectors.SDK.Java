package com.recordpoint.connectors.sdk.service.aggregation.model;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.ServicePayload;
import com.recordpoint.connectors.sdk.service.model.Metadata;
import com.recordpoint.connectors.sdk.service.model.RelationshipData;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

import java.time.Instant;
import java.util.List;

public class AggregationSubmission implements ServicePayload {

    private final String externalId;
    private final String connectorId;
    private final String title;
    private final Instant sourceLastModifiedDate;
    private final String sourceLastModifiedBy;
    private final Instant sourceCreatedDate;
    private final String sourceCreatedBy;
    private final String author;
    private final String location;
    private final String parentExternalId;
    private final List<Metadata> sourceProperties;
    private final List<RelationshipData> relationships;
    private final String mediaType;
    private final String barcodeType;
    private final String barcodeValue;
    private final String recordCategoryId;
    private final String correlationId;

    public AggregationSubmission(Builder builder) {
        Preconditions.checkNotNull(builder.connectorId, MessageFieldProvider.getMessage("field.connectorId"));
        Preconditions.checkNotNull(builder.externalId, MessageFieldProvider.getMessage("field.externalId"));
        Preconditions.checkNotNull(builder.sourceCreatedBy, MessageFieldProvider.getMessage("field.sourceCreatedBy"));
        Preconditions.checkNotNull(builder.sourceCreatedDate, MessageFieldProvider.getMessage("field.sourceCreatedDate"));
        Preconditions.checkNotNull(builder.sourceLastModifiedBy, MessageFieldProvider.getMessage("field.sourceLastModifiedBy"));
        Preconditions.checkNotNull(builder.sourceLastModifiedDate, MessageFieldProvider.getMessage("field.sourceLastModifiedDate"));
        Preconditions.checkNotNull(builder.title, MessageFieldProvider.getMessage("field.title"));
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
        this.sourceProperties = builder.sourceProperties;
        this.relationships = builder.relationships;
        this.mediaType = builder.mediaType;
        this.barcodeType = builder.barcodeType;
        this.barcodeValue = builder.barcodeValue;
        this.recordCategoryId = builder.recordCategoryId;
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

    public String getAuthor() {
        return author;
    }

    public String getLocation() {
        return location;
    }

    public String getParentExternalId() {
        return parentExternalId;
    }

    public List<Metadata> getSourceProperties() {
        return sourceProperties;
    }

    public List<RelationshipData> getRelationships() {
        return relationships;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getBarcodeType() {
        return barcodeType;
    }

    public String getBarcodeValue() {
        return barcodeValue;
    }

    public String getRecordCategoryId() {
        return recordCategoryId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public static class Builder {
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
        private List<Metadata> sourceProperties;
        private List<RelationshipData> relationships;
        private String mediaType;
        private String barcodeType;
        private String barcodeValue;
        private String recordCategoryId;
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

        public Builder setSourceProperties(List<Metadata> sourceProperties) {
            this.sourceProperties = sourceProperties;
            return this;
        }

        public Builder setRelationships(List<RelationshipData> relationships) {
            this.relationships = relationships;
            return this;
        }

        public Builder setMediaType(String mediaType) {
            this.mediaType = mediaType;
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

        public Builder setRecordCategoryId(String recordCategoryId) {
            this.recordCategoryId = recordCategoryId;
            return this;
        }

        public Builder setCorrelationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public AggregationSubmission build() {
            return new AggregationSubmission(this);
        }
    }

}
