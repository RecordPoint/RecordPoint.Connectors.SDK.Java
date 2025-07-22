# Overview
The RecordPoint Connector SDK for Java makes building custom connectors that integrate with RecordPoint's Connector 
Framework easy. This SDK enables developers to create connectors that can synchronize content and records from various data sources 
into RecordPoint and manage disposal events.

# Installation
The RecordPoint Connector SDK is available on Maven Central and can be added to a `pom.xml` file with:

```xml
<dependency>
    <groupId>com.recordpoint</groupId>
    <artifactId>recordpoint-connector-sdk</artifactId>
    <version>1.1.1</version>
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
the OKHttpClient instead by including the following:

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

## Java version
This SDK is designed to work with Java 8 or above.

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
        .setApplicationName("Acme Data Connector")
        .setConnectorId("00000000-0000-0000-0000-000000000000")
        .setTenantId("00000000-0000-0000-0000-000000000000")
        .setClientId("client ID goes here")
        .setSecret(System.getenv("CLIENT_SECRET"))
        .build();
```
*Note:* The example above assumes the use of a standard tenant. If using a dedicated
instance, replace `.setRegion(...)` with `.setBaseUrl("https://connector-.../")`

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
builder.setMediaType(ItemSubmission.MediaType.ELECTRONIC);
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

// Or alternatively use the OptionalMetadata wrapper for easier null-value handling:
builder.setSourceProperties(OptionalMetadata.makeSourceProperties(List.of(
        OptionalMetadata.Builder().of("Foo", 123).orEmpty(),
        OptionalMetadata.Builder().of("Baz", (String) null).orIgnore()
)));

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

## 4. Handling Notifications
Connectors can receive five types of notifications: `Ping`, `ItemDestroyed`, `ConnectorConfigCreated`, `ConnectorConfigUpdated` and `ConnectorConfigCreated`.
The most common and useful event is `ItemDestroyed` which is issues when an item is disposed from the platform. This event should trigger the
secure and permanent destruction of the data in the source system. To make this simple to implement, the connector provides the item details
to allow the connector to easily locate the item to destroy:

```java
NotificationServiceClient notificationServiceClient = NotificationServiceClient.Builder()
        .setServiceSettings(settings)
        .setTokenManager(tokenManager)
        .build();

List<Notification> notifications = notificationServiceClient.getNotificationList(GetNotificationRequest.Builder()
        .setConnectorId(connectorId)
        .build()
);

for (Notification notification : notifications) {
    if (Notification.NotificationType.ITEM_DESTROYED.equals(notification.getNotificationType())) {
        LOG.info(
                "Deleting item: externalID={}; title={}; author={}, sourceProperties={}",
                notification.getItem().getExternalId(), notification.getItem().getTitle(),
                notification.getItem().getAuthor(), notification.getItem().getSourceProperties()
        );
        // Perform deletion here
    }
```

Once the item is destroyed, or an non-retryable error has occurred, the notification should be acknowledged so that the
item can be marked as successfully destroyed or noted that disposal failed:

```java
notificationServiceClient.acknowledgeProcessedNotification(AcknowledgeNotificationRequest.Builder()
        .setNotificationAcknowledge(NotificationAcknowledge.Builder()
                .setConnectorId(connectorId)
                .setNotificationId(notification.getId())
                .setConnectorStatusMessage("Item destroyed using Java SDK!")
                .setProcessingResult(NotificationAcknowledge.ProcessingResult.OK)
                .build()
        )
        .build()
);
}
```

# Examples
## Java Example (`recordpoint-connector-sdk-java-example`)
An example of using the SDK in a Java-based Micronaut application is included in this repository. This includes examples
of authenticating, importing from a CSV file, submitting custom audit events, and managing notifications. The example 
provides a command line application and can be called using:
```shell
# Import a CSV
java -jar target/recordpoint-connector-sdk-java-sample-1.0.2.jar --csv=src/test/resources/books.csv --tenant-id=$TENANT_ID \
--client-id=$CLIENT_ID --connector-id=$CONNECTOR_ID --external-id=id --title=title --author=author --location=location \
--double-properties=rating,msrp --datetime-properties=published --boolean-properties=IsFiction --region=CAC \
--last-modified=2024-01-13T00:00:00Z

# Submit an audit event
java -jar target/recordpoint-connector-sdk-java-sample-1.0.2.jar audit --tenant-id=$TENANT_ID \
--client-id=$CLIENT_ID --connector-id=$CONNECTOR_ID --region=CAC

# Handle notifications
java -jar target/recordpoint-connector-sdk-java-sample-1.0.2.jar notifications --tenant-id=$TENANT_ID \
--client-id=$CLIENT_ID --connector-id=$CONNECTOR_ID --region=CAC
```

This example can also be run using GraalVM by building it as a native image.

## Scala Example (`recordpoint-connector-sdk-scala-example`)
A short example showing the use of the SDK in a Scala test (or application).

## Spark Example (`recordpoint-connector-sdk-spark-example`)
A short example of submitting items from a SparkSQL dataframe using Apache Spark. To run this example, set
```shell
export REGION="CAC"
export TENANT_ID="..."
export CONNECTOR_ID="..."
export CLIENT_ID="..."
export CLIENT_SECRET="..."

# --add-opens flags required if using Java 17+ and Spark <4.0.0
java --add-opens=java.base/sun.nio.ch=ALL-UNNAMED \
     --add-opens=java.base/sun.util.calendar=ALL-UNNAMED \
     -jar target/recordpoint-connector-sdk-spark-sample-1.0.2.jar
```

# Troubleshooting
When raising support requests to RecordPoint support, please include:
 - SDK Version details
 - Application name (for tracing network requests)
 - Debug-level wire logs (if relevant)

## Retrieving version details
The version details of the SDK can be retrieved from `ServiceSettings` using:
```java
System.out.println("Current SDK version: " + ServiceSettings.getCurrentVersion());
```

## Setting application name
RecordPoint may utilise the user agent field to differentiate SDK users, manage load and trace network requests. To set
the user agent, set the `applicationName` property of `ServiceSettings` to a meaningful and unique name for your connector, for example:
```java
ServiceSettings settings = ServiceSettings.Builder()
        .setApplicationName("<company> <connector> <version>")
        // add additional settings here
        .build();
```

## Debug logging
The SDK makes extensive use of the Apache HttpClient and [enabling debug-level logging for the HttpClient's wire 
logging](https://hc.apache.org/httpcomponents-client-4.5.x/logging.html) will provide detailed information for
troubleshooting, including request and response payloads. Please consider sensitivity of the information before
including these on a ticket.

# License
Licensed under Apache 2.0, see LICENSE
