package com.recordpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.recordpoint.connectors.sdk.auth.MsalTokenManager;
import com.recordpoint.connectors.sdk.auth.TokenManager;
import com.recordpoint.connectors.sdk.service.ServiceSettings;
import com.recordpoint.connectors.sdk.service.audit.AuditEventServiceClient;
import com.recordpoint.connectors.sdk.service.audit.SubmitSourceEventRequest;
import com.recordpoint.connectors.sdk.service.audit.model.AuditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.time.Instant;
import java.util.UUID;

@CommandLine.Command(name = "audit", subcommands = {NotificationCommand.class})
public class AuditCommand extends BaseCommand implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(AuditCommand.class);

    @Override
    public void run() {
        LOG.info("Submitting audit event");

        ServiceSettings settings = getServiceSettings();

        try (TokenManager tokenManager = new MsalTokenManager(settings)) {
            AuditEventServiceClient auditEventServiceClient = AuditEventServiceClient.Builder()
                    .setServiceSettings(getServiceSettings())
                    .setTokenManager(tokenManager)
                    .build();

            auditEventServiceClient.submitContentSourceEvent(SubmitSourceEventRequest.Builder()
                    .setAuditEvent(AuditEvent.Builder()
                            .connectorId(connectorId)
                            .itemExternalId("978-0060935467")
                            .eventExternalId(UUID.randomUUID().toString())
                            .description("Processed CSV results and made audit event")
                            .eventType("CSV Example Event")
                            .userName("Test User")
                            .userId(UUID.randomUUID().toString())
                            .createdDate(Instant.now())
                            .build())
                    .build()
            );

        } catch (Exception exc) {
            LOG.error("Unable to import CSV file", exc);
        }
    }
}
