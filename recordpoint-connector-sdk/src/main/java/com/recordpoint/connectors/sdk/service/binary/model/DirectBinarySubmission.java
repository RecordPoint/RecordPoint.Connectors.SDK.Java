package com.recordpoint.connectors.sdk.service.binary.model;

import com.google.common.base.Preconditions;
import com.recordpoint.connectors.sdk.service.ServicePayload;
import com.recordpoint.connectors.sdk.service.util.MessageFieldProvider;

import java.time.Instant;

public class DirectBinarySubmission implements ServicePayload {

    private final String mimeType;
    private final long fileSize;
    private final String fileHash;
    private final Instant sourceLastModifiedDate;
    private final String connectorId;
    private final String itemExternalId;
    private final String binaryExternalId;
    private final String fileName;
    private final String location;
    private final String correlationId;
    private final boolean isOldVersion;
    private final boolean skipEnrichment;

    public DirectBinarySubmission(Builder builder) {
        Preconditions.checkNotNull(builder.binaryExternalId, MessageFieldProvider.getMessage("field.binaryExternalId"));
        Preconditions.checkNotNull(builder.connectorId, MessageFieldProvider.getMessage("field.connectorId"));
        Preconditions.checkNotNull(builder.itemExternalId, MessageFieldProvider.getMessage("field.itemExternalId"));
        this.mimeType = builder.mimeType;
        this.fileSize = builder.fileSize;
        this.fileHash = builder.fileHash;
        this.sourceLastModifiedDate = builder.sourceLastModifiedDate;
        this.connectorId = builder.connectorId;
        this.itemExternalId = builder.itemExternalId;
        this.binaryExternalId = builder.binaryExternalId;
        this.fileName = builder.fileName;
        this.location = builder.location;
        this.correlationId = builder.correlationId;
        this.isOldVersion = builder.isOldVersion;
        this.skipEnrichment = builder.skipEnrichment;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public String getMimeType() {
        return mimeType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getFileHash() {
        return fileHash;
    }

    public Instant getSourceLastModifiedDate() {
        return sourceLastModifiedDate;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public String getItemExternalId() {
        return itemExternalId;
    }

    public String getBinaryExternalId() {
        return binaryExternalId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getLocation() {
        return location;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public boolean isOldVersion() {
        return isOldVersion;
    }

    public boolean isSkipEnrichment() {
        return skipEnrichment;
    }

    public static class Builder {
        private String mimeType;
        private long fileSize;
        private String fileHash;
        private Instant sourceLastModifiedDate;
        private String connectorId;
        private String itemExternalId;
        private String binaryExternalId;
        private String fileName;
        private String location;
        private String correlationId;
        private boolean isOldVersion;
        private boolean skipEnrichment;

        public Builder mimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public Builder fileSize(long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public Builder fileHash(String fileHash) {
            this.fileHash = fileHash;
            return this;
        }

        public Builder sourceLastModifiedDate(Instant sourceLastModifiedDate) {
            this.sourceLastModifiedDate = sourceLastModifiedDate;
            return this;
        }

        public Builder connectorId(String connectorId) {
            this.connectorId = connectorId;
            return this;
        }

        public Builder itemExternalId(String itemExternalId) {
            this.itemExternalId = itemExternalId;
            return this;
        }

        public Builder binaryExternalId(String binaryExternalId) {
            this.binaryExternalId = binaryExternalId;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder correlationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public Builder isOldVersion(boolean isOldVersion) {
            this.isOldVersion = isOldVersion;
            return this;
        }

        public Builder skipEnrichment(boolean skipEnrichment) {
            this.skipEnrichment = skipEnrichment;
            return this;
        }

        public DirectBinarySubmission build() {
            return new DirectBinarySubmission(this);
        }
    }

}
