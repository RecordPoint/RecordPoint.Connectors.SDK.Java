# Overview
The RecordPoint Connector SDK for Java makes building custom connectors that integrate with RecordPoint's Connector 
Framework easy.
This SDK enables developers to create connectors that can synchronize content and records from various data sources 
into RecordPoint and manage disposal events.

# Installation
The RecordPoint Connector SDK is available on Maven Central and can be added to a `pom.xml` file with:

```xml
<dependency>
    <groupId>com.recordpoint</groupId>
    <artifactId>recordpoint-connector-sdk</artifactId>
    <version>1.0.1</version>
</dependency>
```

*Note:* Check [Maven Central](https://central.sonatype.com/artifact/com.recordpoint/recordpoint-connector-sdk) for the latest version to use.

## Microsoft Entra ID
If using Microsoft Entra ID for authentication (the default), the Microsoft `azure-identity` library must also be included:

```xml
<dependency>
    <groupId>com.azure</groupId>
    <artifactId>azure-identity</artifactId>
</dependency>
```

In some environments and runtimes, the `netty` dependency in `azure-identity` may cause issues and can be replaced with 
the OKHttpClient instead by including the following instead:

```xml
<dependency>
    <groupId>com.azure</groupId>
    <artifactId>azure-identity</artifactId>
    <exclusions>
        <exclusion>
            <groupId>com.azure</groupId>
            <artifactId>azure-core-http-netty</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>com.azure</groupId>
    <artifactId>azure-core-http-okhttp</artifactId>
</dependency>
```

*Note:* Replacing the `netty` dependency will prevent interactive authentication from working, but this is typically not 
used in conjunction with the RecordPoint SDK.

# Documentation
JavaDoc is available at [javadoc.io](https://javadoc.io/doc/com.recordpoint/recordpoint-connector-sdk/latest/index.html) or see below for helpful examples.

# Usage
## 1. Creating an authentication manager
To use the SDK, you must provide a `TokenManager` that can provide an authentication token. 
If multithreading is to be used, the manager must be thread safe. 
By default, a thread-safe `TokenManager` is provided for Microsoft Entra ID that requires the `azure-identity` library
discussed above. Alternatively, a simple implementation `TokenManager` that reads an environment variable is
shown in the javadoc.

To use the `MsalTokenManager` the Service Settings must be defined, including the tenant ID,
RecordPoint API endpoint and connector ID. Additionally, the Service Settings also contain the
OAuth2.0 credentials if using the `MsalTokenManager`. These settings can be defined explicitly:
```java
ServiceSettings settings = ServiceSettings.Builder()
        .setRegion(ServiceSettings.Regions.CAC)
        .setConnectorId("00000000-0000-0000-0000-000000000000")
        .setTenantId("00000000-0000-0000-0000-000000000000")
        .setClientId("client ID goes here")
        .setSecret(System.getenv("CLIENT_SECRET"))
        .build();
```
*Note:* The example above assumes the use of a standard tenant. If using a dedicated
instance, replace `.setRegion(...)` with `.setBaseUrl("https://connector-.../")`

Alternatively, the JSON file generated from the RecordPoint Connector Administration UI 
can be used:

```java
ServiceSettings settings = ServiceSettings.Builder()
        .fromJsonFile(Path.of("connectorSettings.json"))
        .setSecret(System.getenv("CLIENT_SECRET"))
        .build();
```

These settings can be passed directly to the `MsalTokenManager`:
```java
try (TokenManager tokenManager = new MsalTokenManager(settings)) {
    // ...
}
```

## 2. Submitting a record
Once a `TokenManager` is instantiated, it can be used with an `ItemServiceClient` to
submit an item:

```java
ItemServiceClient itemServiceClient = ItemServiceClient.Builder()
        .setServiceSettings(settings)
        .setTokenManager(tokenManager)
        .build();

ItemSubmission.Builder builder = new ItemSubmission.Builder();
String externalId = UUID.randomUUID().toString()
builder.setConnectorId(settings.getConnectorId());
builder.setAuthor("Douglas Adams");
builder.setExternalId(externalId);
builder.setLocation("https://example.com/book.pdf");
builder.setParentExternalId(null);
builder.setTitle("The Hitchhiker's Guide to the Galaxy");
builder.setMediaType("Electronic");
builder.setSourceCreatedBy("Douglas Adams");
builder.setSourceLastModifiedBy("Thomas Tidholm");
builder.setParentExternalId("0");

builder.setSourceLastModifiedDate(Instant.now());
builder.setSourceCreatedDate(Instant.now());

// Custom metadata
builder.setSourceProperties(List.of(
        Metadata.of("Pages", 216),
        Metadata.of("Rating", 4.95),
        Metadata.of("Category", "Science Fiction"),
        Metadata.of("IsFiction", true)
));

SubmitItemRequest request = SubmitItemRequest.Builder()
        .setPayload(builder.build())
        .build();

itemServiceClient.submitItem(request);
```

## 3. Submitting a binary (optional)
To submit a binary, use the Binary Service client:

```java
binaryServiceClient = BinaryServiceClient.Builder()
        .setServiceSettings(settings)
        .setTokenManager(tokenManager)
        .build();

binaryServiceClient.submitFile(SubmitFileRequest.Builder()
        .setFileContent(new ByteArrayInputStream(...))
        .setFileSubmissionInfo(DirectBinarySubmission.Builder()
                .itemExternalId(externalId)
                .connectorId(settings.getConnectorId()))
                .binaryExternalId(externalId + "-1")
                .fileName("book.pdf")
                .mimeType("application/pdf")
                .fileSize(...)
                .build()
        )
        .build());
```

## 3. Audit Events
```java
AuditEventServiceClient auditEventServiceClient = AuditEventServiceClient.Builder()
        .setServiceSettings(settings)
        .setTokenManager(tokenManager)
        .build();

auditEventServiceClient.submitContentSourceEvent(SubmitSourceEventRequest.Builder()
        .setAuditEvent(AuditEvent.Builder()
                .connectorId(connectorId)
                .itemExternalId(externalId)
                .eventExternalId(UUID.randomUUID().toString())
                .description("Example Event Description")
                .eventType("Example Event")
                .userName("Eoin Colfer")
                .userId(UUID.randomUUID().toString())
                .createdDate(Instant.now())
                .build())
        .build());
```

Other clients available include:

- Aggregations
- Notifications (for receiving and sending disposal notifications)

# License
Licensed under Apache 2.0, see LICENSE