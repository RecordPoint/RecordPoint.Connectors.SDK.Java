package com.recordpoint;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.collect.Streams;
import com.recordpoint.connectors.sdk.auth.MsalTokenManager;
import com.recordpoint.connectors.sdk.auth.TokenManager;
import com.recordpoint.connectors.sdk.service.ServiceSettings;
import com.recordpoint.connectors.sdk.service.binary.BinaryServiceClient;
import com.recordpoint.connectors.sdk.service.binary.SubmitFileRequest;
import com.recordpoint.connectors.sdk.service.binary.model.DirectBinarySubmission;
import com.recordpoint.connectors.sdk.service.item.ItemServiceClient;
import com.recordpoint.connectors.sdk.service.item.SubmitItemRequest;
import com.recordpoint.connectors.sdk.service.item.model.ItemSubmission;
import com.recordpoint.connectors.sdk.service.model.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@CommandLine.Command(name = "import", subcommands = {NotificationCommand.class})
public class ImportCommand extends BaseCommand implements Runnable {
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    private static final CsvMapper mapper = new CsvMapper();

    private static final Logger LOG = LoggerFactory.getLogger(ImportCommand.class);

    private static final String RECORD_TYPE = "Electronic";

    private static final String DEFAULT_PARENT_ID = "1";

    private static final String DEFAULT_ITEM_VERSION = "1";

    @CommandLine.Option(
            names = {"--csv"},
            required = true,
            description = "Path to CSV file to perform import"
    )
    private Path csvFilepath;

    @CommandLine.Option(
            names = {"--external-id"},
            required = true,
            description = "Column name for the item external ID"
    )
    private String externalIdColumn;

    @CommandLine.Option(
            names = {"--title"},
            required = true,
            description = "Column name for the item Title field"
    )
    private String titleColumn;

    @CommandLine.Option(
            names = {"--author"},
            required = true,
            description = "Column name for the item author"
    )
    private String authorColumn;

    @CommandLine.Option(
            names = {"--location"},
            required = true,
            description = "Column name for the item location"
    )
    private String locationColumn;

    @CommandLine.Option(
            names = {"--boolean-properties"},
            description = "List of source property columns that are booleans"
    )
    private List<String> booleanColumns;

    @CommandLine.Option(
            names = {"--double-properties"},
            description = "List of source property columns that are doubles"
    )
    private List<String> doubleColumns;

    @CommandLine.Option(
            names = {"--integer-properties"},
            description = "List of source property columns that are integers"
    )
    private List<String> integerColumns;


    @CommandLine.Option(
            names = {"--datetime-properties"},
            description = "List of source property columns that are datetimes"
    )
    private List<String> datetimeColumns;

    @CommandLine.Option(
            names = {"--last-modified"},
            required = true,
            description = "Column containing the last modified timestamp or fixed value to use"
    )
    private String lastModifiedColumn;

    private ItemServiceClient itemServiceClient;

    private BinaryServiceClient binaryServiceClient;

    protected Metadata convertMapToMetadataList(Map.Entry<String, String> property) {
        if (doubleColumns != null && doubleColumns.contains(property.getKey())) {
            return Metadata.of(property.getKey(), Double.valueOf(property.getValue()));
        }

        if (integerColumns != null && integerColumns.contains(property.getKey())) {
            return Metadata.of(property.getKey(), Integer.valueOf(property.getValue()));
        }

        if (booleanColumns != null && booleanColumns.contains(property.getKey())) {
            return Metadata.of(property.getKey(), Boolean.valueOf(property.getValue()));
        }

        if (datetimeColumns != null && datetimeColumns.contains(property.getKey())) {
            try {
                return Metadata.of(property.getKey(), Instant.parse(property.getValue()));
            } catch (DateTimeParseException exc) {
                return Metadata.of(property.getKey(), LocalDate.parse(property.getValue()).atStartOfDay(ZoneOffset.UTC).toInstant());
            }
        }

        return Metadata.of(property.getKey(), property.getValue());
    }

    protected ItemSubmission buildItemSubmission(Map<String, String> properties) {
        ItemSubmission.Builder builder = new ItemSubmission.Builder();
        builder.setConnectorId(connectorId);

        builder.setAuthor(properties.get(authorColumn));
        builder.setContentVersion(DEFAULT_ITEM_VERSION);
        builder.setExternalId(properties.get(externalIdColumn));
        builder.setLocation(properties.get(locationColumn));
        builder.setTitle(properties.get(titleColumn));
        builder.setMediaType(ItemSubmission.MediaType.ELECTRONIC);
        builder.setSourceCreatedBy(properties.get(authorColumn));
        builder.setSourceLastModifiedBy(properties.get(authorColumn));
        builder.setParentExternalId(DEFAULT_PARENT_ID);

        if (properties.get(lastModifiedColumn) != null) {
            builder.setSourceLastModifiedDate(Instant.parse(properties.get(lastModifiedColumn)));
            builder.setSourceCreatedDate(Instant.parse(properties.get(lastModifiedColumn)));
        } else {
            builder.setSourceLastModifiedDate(Instant.parse(lastModifiedColumn));
            builder.setSourceCreatedDate(Instant.parse(lastModifiedColumn));
        }

        builder.setSourceProperties(
                properties.entrySet().stream()
                        .map(this::convertMapToMetadataList)
                        .toList()
        );

        return builder.build();
    }

    protected void processCsvRow(Map<String, String> row) {
        LOG.info("Processing row: {}", row);

        try {
            SubmitItemRequest request = SubmitItemRequest.Builder()
                    .setPayload(buildItemSubmission(row))
                    .build();

            itemServiceClient.submitItem(request);

            String data = jsonMapper.writeValueAsString(row);

            binaryServiceClient.submitFile(SubmitFileRequest.Builder()
                    .setFileContent(new ByteArrayInputStream(data.getBytes()))
                    .setFileSubmissionInfo(DirectBinarySubmission.Builder()
                            .itemExternalId(row.get(externalIdColumn))
                            .connectorId(connectorId)
                            .binaryExternalId(UUID.randomUUID().toString())
                            .fileName(row.get(titleColumn) + ".json")
                            .mimeType("application/json")
                            .fileSize(data.getBytes().length)
                            .build()
                    )
                    .build()
            );
        } catch (Exception exc) {
            LOG.error("Unable to submit item: {}", row, exc);
        }
    }

    @Override
    public void run() {
        LOG.info("Ingesting CSV: {}", csvFilepath);

        ServiceSettings settings = getServiceSettings();

        try (TokenManager tokenManager = new MsalTokenManager(settings)) {
            itemServiceClient = ItemServiceClient.Builder()
                    .setServiceSettings(settings)
                    .setTokenManager(tokenManager)
                    .build();

            binaryServiceClient = BinaryServiceClient.Builder()
                    .setServiceSettings(settings)
                    .setTokenManager(tokenManager)
                    .build();

            CsvSchema schema = CsvSchema.emptySchema().withHeader();

            MappingIterator<Map<String, String>> iterator = mapper
                    .readerForMapOf(String.class)
                    .with(schema)
                    .readValues(Files.newInputStream(csvFilepath));

            long totalRows = Streams.stream(iterator)
                    .parallel()
                    .map(Utils.passthrough(this::processCsvRow))
                    .filter(Objects::nonNull)
                    .count();

            LOG.info("Processed {} rows", totalRows);

        } catch (Exception exc) {
            LOG.error("Unable to import CSV file", exc);
        }
    }
}
