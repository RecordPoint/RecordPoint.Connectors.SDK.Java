package com.recordpoint;

import com.recordpoint.connectors.sdk.service.ServiceSettings;
import picocli.CommandLine;

public class BaseCommand {
    @CommandLine.Option(
            names = {"--region"},
            required = false,
            description = "Region of the service (region or region URL must be specified)"
    )
    protected ServiceSettings.Regions region;

    @CommandLine.Option(
            names = {"--region-url"},
            required = false,
            description = "URL of the service (region or region URL must be specified)"
    )
    protected String regionUrl;

    @CommandLine.Option(
            names = {"--tenant-id"},
            required = true,
            description = "ID of the tenant hosting the connector"
    )
    protected String tenantId;

    @CommandLine.Option(
            names = {"--connector-id"},
            required = true,
            description = "ID of the connector to ingest into"
    )
    protected String connectorId;

    @CommandLine.Option(
            names = {"--client-id"},
            required = true,
            description = "OAuth2.0 client IDD"
    )
    protected String clientId;

    @CommandLine.Option(
            names = {"--client-secret"},
            required = false,
            description = "OAuth2.0 client secret (can also be passed in via CLIENT_SECRET)"
    )
    protected String clientSecret;

    protected ServiceSettings getServiceSettings() {
        ServiceSettings.Builder builder = ServiceSettings.Builder()
                .setConnectorId(connectorId)
                .setTenantId(tenantId)
                .setClientId(clientId)
                .setSecret(clientSecret == null ? System.getenv("CLIENT_SECRET") : clientSecret);

        if (region != null) {
            builder = builder.setRegion(region);
        } else if (regionUrl != null) {
            builder = builder.setBaseUrl(regionUrl);
        }

        return builder.build();
    }
}
