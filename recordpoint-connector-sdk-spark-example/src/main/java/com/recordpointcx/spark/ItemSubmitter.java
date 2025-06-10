package com.recordpointcx.spark;

import com.recordpoint.connectors.sdk.auth.TokenManager;
import com.recordpoint.connectors.sdk.http.exception.HttpExecutionException;
import com.recordpoint.connectors.sdk.http.exception.HttpResponseException;
import com.recordpoint.connectors.sdk.json.JsonMapperException;
import com.recordpoint.connectors.sdk.service.ServiceSettings;
import com.recordpoint.connectors.sdk.service.binary.BinaryServiceClient;
import com.recordpoint.connectors.sdk.service.binary.SubmitFileRequest;
import com.recordpoint.connectors.sdk.service.binary.model.DirectBinarySubmission;
import com.recordpoint.connectors.sdk.service.item.ItemServiceClient;
import com.recordpoint.connectors.sdk.service.item.SubmitItemRequest;
import com.recordpoint.connectors.sdk.service.item.model.ItemSubmission;
import com.recordpoint.connectors.sdk.service.model.Metadata;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemSubmitter {
    private static ItemSubmitter instance;

    private final ServiceSettings serviceSettings;

    private final TokenManager tokenManager;

    private final ItemServiceClient itemServiceClient;

    private final BinaryServiceClient binaryServiceClient;

    private String authorColumn;
    private String externalIdColumn;
    private String locationColumn;
    private String titleColumn;
    private String createdByColumn;
    private String lastModifiedByColumn;
    private String lastModifiedColumn;
    private String createdDateColumn;

    private boolean submitBinary = false;

    private ItemSubmitter(ServiceSettings serviceSettings, TokenManager tokenManager) throws JsonMapperException {
        this.serviceSettings = serviceSettings;
        this.tokenManager = tokenManager;
        itemServiceClient = ItemServiceClient.Builder()
                .setServiceSettings(serviceSettings)
                .setTokenManager(tokenManager)
                .build();

        binaryServiceClient = BinaryServiceClient.Builder()
                .setServiceSettings(serviceSettings)
                .setTokenManager(tokenManager)
                .build();
    }

    public static ItemSubmitter getInstance(ServiceSettings serviceSettings, TokenManager tokenManager) throws JsonMapperException {
        if (instance == null) {
            instance = new ItemSubmitter(serviceSettings, tokenManager);
        }

        return instance;
    }

    public static ItemSubmitter getInstance() throws JsonMapperException {
        if (instance == null) {
            throw new IllegalStateException("ItemSubmitter has not been initialized");
        }

        return instance;
    }

    public void setAuthorColumn(String authorColumn) {
        this.authorColumn = authorColumn;
    }

    public void setExternalIdColumn(String externalIdColumn) {
        this.externalIdColumn = externalIdColumn;
    }

    public void setLocationColumn(String locationColumn) {
        this.locationColumn = locationColumn;
    }

    public void setTitleColumn(String titleColumn) {
        this.titleColumn = titleColumn;
    }

    public void setCreatedByColumn(String createdByColumn) {
        this.createdByColumn = createdByColumn;
    }

    public void setLastModifiedByColumn(String lastModifiedByColumn) {
        this.lastModifiedByColumn = lastModifiedByColumn;
    }

    public void setCreatedDateColumn(String createdDateColumn) {
        this.createdDateColumn = createdDateColumn;
    }

    public void setLastModifiedColumn(String lastModifiedColumn) {
        this.lastModifiedColumn = lastModifiedColumn;
    }

    public void setSubmitBinary(boolean submitBinary) {
        this.submitBinary = submitBinary;
    }

    public ServiceSettings getServiceSettings() {
        return serviceSettings;
    }

    protected Metadata mapField(Row row, StructField field) {
        try {
            if (field.dataType().sameType(DataTypes.BooleanType)) {
                return Metadata.of(field.name(), row.getBoolean(row.fieldIndex(field.name())));
            }

            if (field.dataType().sameType(DataTypes.StringType)) {
                return Metadata.of(field.name(), row.getString(row.fieldIndex(field.name())));
            }

            if (field.dataType().sameType(DataTypes.IntegerType)) {
                return Metadata.of(field.name(), row.getInt(row.fieldIndex(field.name())));
            }

            if (field.dataType().sameType(DataTypes.LongType)) {
                return Metadata.of(field.name(), (int) row.getLong(row.fieldIndex(field.name())));
            }

            if (field.dataType().sameType(DataTypes.DoubleType)) {
                return Metadata.of(field.name(), row.getDouble(row.fieldIndex(field.name())));
            }

            if (field.dataType().sameType(DataTypes.FloatType)) {
                return Metadata.of(field.name(), (double) row.getFloat(row.fieldIndex(field.name())));
            }

            if (field.dataType().sameType(DataTypes.DateType)) {
                return Metadata.of(field.name(), row.getDate(row.fieldIndex(field.name())).toInstant());
            }

            if (field.dataType().sameType(DataTypes.TimestampType)) {
                return Metadata.of(field.name(), row.getInstant(row.fieldIndex(field.name())));
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    protected ItemSubmission buildItemSubmission(Row row) {
        ItemSubmission.Builder builder = new ItemSubmission.Builder();
        builder.setConnectorId(serviceSettings.getConnectorId());

        builder.setAuthor(row.getString(row.fieldIndex(authorColumn)));
        builder.setContentVersion("1");
        builder.setExternalId(row.getString(row.fieldIndex(externalIdColumn)));
        builder.setLocation(row.getString(row.fieldIndex(locationColumn)));
        builder.setTitle(row.getString(row.fieldIndex(titleColumn)));
        builder.setMediaType(ItemSubmission.MediaType.ELECTRONIC);
        builder.setSourceCreatedBy(row.getString(row.fieldIndex(createdByColumn)));
        builder.setSourceLastModifiedBy(row.getString(row.fieldIndex(lastModifiedByColumn)));
        builder.setParentExternalId("0");

        builder.setSourceLastModifiedDate(row.getInstant(row.fieldIndex(lastModifiedColumn)));
        builder.setSourceCreatedDate(row.getInstant(row.fieldIndex(createdDateColumn)));

        builder.setSourceProperties(
                Arrays.stream(row.schema().fields()).map(field -> mapField(row, field))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );

        return builder.build();
    }

    public void submitItem(Row row) throws JsonMapperException, HttpResponseException, HttpExecutionException {
        SubmitItemRequest request = SubmitItemRequest.Builder()
                .setPayload(buildItemSubmission(row))
                .build();

        itemServiceClient.submitItem(request);

        if (!submitBinary) {
            return;
        }

        String data = row.prettyJson();

        binaryServiceClient.submitFile(SubmitFileRequest.Builder()
                .setFileContent(new ByteArrayInputStream(data.getBytes()))
                .setFileSubmissionInfo(DirectBinarySubmission.Builder()
                        .itemExternalId(row.getString(row.fieldIndex(externalIdColumn)))
                        .connectorId(serviceSettings.getConnectorId())
                        .binaryExternalId(row.getString(row.fieldIndex(externalIdColumn)))
                        .fileName(row.getString(row.fieldIndex(externalIdColumn)) + ".json")
                        .mimeType("application/json")
                        .fileSize(data.getBytes().length)
                        .sourceLastModifiedDate(Instant.now())
                        .build()
                )
                .build());
    }

    public void close() throws IOException {
        if (tokenManager != null) {
            tokenManager.close();
        }
    }
}
