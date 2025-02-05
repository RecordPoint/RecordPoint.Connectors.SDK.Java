import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestInstance;
import com.recordpoint.connectors.sdk.auth.*;
import com.recordpoint.connectors.sdk.http.exception.HttpExecutionException;
import com.recordpoint.connectors.sdk.http.exception.HttpResponseException;
import com.recordpoint.connectors.sdk.json.JsonMapperException;
import com.recordpoint.connectors.sdk.service.ServiceSettings;
import com.recordpoint.connectors.sdk.service.aggregation.AggregationServiceClient;
import com.recordpoint.connectors.sdk.service.aggregation.GetAggregationRequest;
import com.recordpoint.connectors.sdk.service.aggregation.SubmitAggregationRequest;
import com.recordpoint.connectors.sdk.service.aggregation.model.Aggregation;
import com.recordpoint.connectors.sdk.service.aggregation.model.AggregationSubmission;
import com.recordpoint.connectors.sdk.service.audit.AuditEventServiceClient;
import com.recordpoint.connectors.sdk.service.audit.SubmitSourceEventRequest;
import com.recordpoint.connectors.sdk.service.audit.model.AuditEvent;
import com.recordpoint.connectors.sdk.service.binary.BinaryServiceClient;
import com.recordpoint.connectors.sdk.service.binary.GetBlobTokenResourceRequest;
import com.recordpoint.connectors.sdk.service.binary.SubmitFileRequest;
import com.recordpoint.connectors.sdk.service.binary.model.DirectBinarySubmission;
import com.recordpoint.connectors.sdk.service.binary.model.DirectBinarySubmissionOutput;
import com.recordpoint.connectors.sdk.service.connector.ConnectorServiceClient;
import com.recordpoint.connectors.sdk.service.connector.GetConnectorConfigRequest;
import com.recordpoint.connectors.sdk.service.item.GetItemsRequest;
import com.recordpoint.connectors.sdk.service.item.ItemServiceClient;
import com.recordpoint.connectors.sdk.service.item.SubmitItemRequest;
import com.recordpoint.connectors.sdk.service.item.model.ItemAcceptance;
import com.recordpoint.connectors.sdk.service.item.model.ItemSubmission;
import com.recordpoint.connectors.sdk.service.item.model.ItemSubmissionOutput;
import com.recordpoint.connectors.sdk.service.model.ConnectorConfig;
import com.recordpoint.connectors.sdk.service.model.Metadata;
import com.recordpoint.connectors.sdk.service.notification.GetNotificationRequest;
import com.recordpoint.connectors.sdk.service.notification.NotificationServiceClient;
import com.recordpoint.connectors.sdk.service.notification.model.Notification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpRequestTest {

    private static final String CONNECTOR_ID = System.getenv("CONNECTOR_ID");
    private static final String AGGREGATION_ID = System.getenv().getOrDefault("AGGREGATION_ID", "01942a27-b4d9-7bb9-aa7b-39384fad0d35");
    private static final String AGGREGATION_NAME = "TestAggregation";

    private static final String CLIENT_ID = System.getenv("CLIENT_ID");
    private static final String CLIENT_SECRET  = System.getenv("CLIENT_SECRET");

    private static final String TENANT_ID = System.getenv().getOrDefault("TENANT_ID", "893805a7-92f4-4689-ae1d-37fb9c5c8e6d");

    private final AggregationServiceClient aggregationClient;
    private final AuditEventServiceClient auditClient;
    private final BinaryServiceClient binaryClient;
    private final ConnectorServiceClient connectorClient;
    private final ItemServiceClient itemClient;
    private final NotificationServiceClient notificationClient;
    private final ServiceSettings settings;
    private final TokenManager tokenManager;

    public HttpRequestTest() throws JsonMapperException {
        assertNotNull(CLIENT_SECRET, "No environment variable named 'CLIENT_SECRET' set");
        assertNotNull(CLIENT_ID, "No environment variable named 'CLIENT_ID' set");

        settings = ServiceSettings.Builder()
                .fromJsonFile(Paths.get(getClass().getClassLoader().getResource("settings.json").getPath()))
                .setClientId(CLIENT_ID)
                .setSecret(CLIENT_SECRET)
                .build();
        this.tokenManager = new MsalTokenManager(settings);

        this.aggregationClient = AggregationServiceClient.Builder()
                .setServiceSettings(settings)
                .setTokenManager(tokenManager)
                .build();
        this.auditClient = AuditEventServiceClient.Builder()
                .setServiceSettings(settings)
                .setTokenManager(tokenManager)
                .build();
        this.binaryClient = BinaryServiceClient.Builder()
                .setServiceSettings(settings)
                .setTokenManager(tokenManager)
                .build();
        this.connectorClient = ConnectorServiceClient.Builder()
                .setServiceSettings(settings)
                .setTokenManager(tokenManager)
                .build();
        this.itemClient = ItemServiceClient.Builder()
                .setServiceSettings(settings)
                .setTokenManager(tokenManager)
                .build();
        this.notificationClient = NotificationServiceClient.Builder()
                .setServiceSettings(settings)
                .setTokenManager(tokenManager)
                .build();
    }

    @AfterAll
    public void cleanUp() {
        try {
            tokenManager.close();
        } catch (IOException ignored) {
        }
    }

    @Nested
    class GeneralTest {

        @Disabled
        void testFailedCredentials() {
            try (TokenRequest requestToken = TokenRequest.Builder()
                    .setTenantId(TENANT_ID)
                    .setClientId(System.getenv("CLIENT_ID"))
                    .setSecret("not the right secret")
                    .build()) {

                TokenResponseException exception = assertThrows(TokenResponseException.class, requestToken::getToken);
                assertNotNull(exception.getDetail());

            } catch (IOException ignored) {
            }
        }

        @Disabled
        void testTokenCredentials() throws TokenResponseException {
            try (TokenRequest requestToken = TokenRequest.Builder()
                    .setTenantId(TENANT_ID)
                    .setClientId(System.getenv("CLIENT_ID"))
                    .setSecret(System.getenv("CLIENT_SECRET"))
                    .build()) {

                Token token = requestToken.getToken();
                assertNotNull(token);
            } catch (IOException ignored) {
            }
        }

        @Disabled
        void testFailedBuildAggregationRequest() {
            NullPointerException exception = assertThrows(NullPointerException.class,
                    () -> GetAggregationRequest.Builder()
                            .setFieldValue("TestConnector")
                            .build());

            assertEquals("Field 'fieldName' is required and cannot be empty. Please provide the name of the metadata field.", exception.getMessage());
        }

        @Disabled
        void testFailedAggregationService() {
            GetAggregationRequest request = GetAggregationRequest.Builder()
                    .setFieldName("ContentSources")
                    .setFieldValue("FirstTestConnector")
                    .build();

            HttpResponseException exception = assertThrows(HttpResponseException.class,
                    () -> aggregationClient.getAggregationList(request));

            assertEquals("API Error", exception.getTitle());
        }

        @Disabled
        void testAggregationService() throws JsonMapperException, HttpExecutionException, HttpResponseException {
            GetAggregationRequest request = GetAggregationRequest.Builder()
                    .setFieldName("ContentSource")
                    .setFieldValue("FirstTestConnector")
                    .build();

            List<Aggregation> response = aggregationClient.getAggregationList(request);

            assertNotNull(response);
            assertTrue(response.size() <= 20);
        }

        @Disabled
        void testNotificationService() throws JsonMapperException, HttpExecutionException, HttpResponseException {

            GetNotificationRequest request = GetNotificationRequest.Builder()
                    .setConnectorId(CONNECTOR_ID)
                    .build();

            List<Notification> response = notificationClient.getNotificationList(request);

            assertNotNull(response);
        }

        @Disabled
        void testPostAggregation() throws JsonMapperException, HttpExecutionException, HttpResponseException {

            SubmitAggregationRequest request = SubmitAggregationRequest.Builder()
                    .setPayload(AggregationSubmission.Builder()
                            .setAuthor("java SDK")
                            .setExternalId(AGGREGATION_ID)
                            .setLocation("/test/test.text")
                            .setParentExternalId(UUID.randomUUID().toString())
                            .setSourceCreatedBy("java SDK")
                            .setSourceCreatedDate(Instant.now())
                            .setSourceLastModifiedBy("java SDK")
                            .setTitle(AGGREGATION_NAME)
                            .setSourceLastModifiedDate(Instant.now())
                            .setConnectorId(CONNECTOR_ID)
                            .build())
                    .build();

            assertTrue(aggregationClient.submitAggregation(request));

        }

        @Disabled
        void testFailedPostAggregation() {

            SubmitAggregationRequest request = SubmitAggregationRequest.Builder()
                    .setPayload(AggregationSubmission.Builder()
                            .setAuthor("java SDK")
                            .setExternalId(UUID.randomUUID().toString())
                            .setLocation("/test/test.text")
                            .setParentExternalId(UUID.randomUUID().toString())
                            .setSourceCreatedBy("java SDK")
                            .setSourceCreatedDate(Instant.now())
                            .setSourceLastModifiedBy("java SDK")
                            .setTitle("test aggregation")
                            .setSourceLastModifiedDate(Instant.now())
                            .build())
                    .build();

            HttpResponseException exception = assertThrows(HttpResponseException.class,
                    () -> aggregationClient.submitAggregation(request));
            assertEquals("API Error", exception.getTitle());
            assertNotNull(exception.getMessage());
        }

        @Disabled
        void testPostItem() throws JsonMapperException, HttpExecutionException, HttpResponseException {

            SubmitItemRequest request = SubmitItemRequest.Builder()
                    .setPayload(ItemSubmission.Builder()
                            .setAuthor("java SDK")
                            .setExternalId(UUID.randomUUID().toString())
                            .setLocation("/test/test.text")
                            .setParentExternalId(AGGREGATION_ID)
                            .setSourceCreatedBy("java SDK")
                            .setSourceCreatedDate(Instant.now())
                            .setSourceLastModifiedBy("java SDK")
                            .setTitle("test aggregation")
                            .setSourceLastModifiedDate(Instant.now())
                            .setConnectorId(CONNECTOR_ID)
                            .setMimeType("pdf")
                            .setContentVersion("v1.0")
                            .setMediaType("Electronic")
                            .build())
                    .build();

            ItemAcceptance response = itemClient.submitItem(request);

            assertNotNull(response);

        }

        @Disabled
        void testItemService() throws JsonMapperException, HttpExecutionException, HttpResponseException {
            GetItemsRequest request = GetItemsRequest.Builder()
                    .setFieldName("ContentSource")
                    .setFieldValue("FirstTestConnector")
                    .build();

            List<ItemSubmissionOutput> response = itemClient.getItemsList(request);

            assertNotNull(response);
            assertTrue(response.size() <= 20 && !response.isEmpty());
        }

        @Disabled
        void testFailedGetConnectorConfigs() {
            GetConnectorConfigRequest request = GetConnectorConfigRequest.Builder()
                    .setId(UUID.randomUUID().toString())
                    .build();

            HttpResponseException exception = assertThrows(HttpResponseException.class,
                    () -> connectorClient.getConnectorConfiguration(request));

            assertEquals("API Error", exception.getTitle());
            assertNotEquals(0, exception.getStatusCode());
            assertNotNull(exception.getMessage());
        }

        @Disabled
        void testGetConnectorsConfigs() throws JsonMapperException, HttpExecutionException, HttpResponseException {
            List<ConnectorConfig> response = connectorClient.getConnectorConfigurations();

            assertNotNull(response);
        }

        @Disabled
        void testPutAuditEvent() {
            SubmitSourceEventRequest request = SubmitSourceEventRequest.Builder()
                    .setAuditEvent(AuditEvent.Builder()
                            .connectorId(settings.getConnectorId())
                            .itemExternalId("978-0060935467")
                            .eventExternalId(UUID.randomUUID().toString())
                            .description("Processed CSV results and made audit event")
                            .eventType("CSV Import")
                            .userName("Test User")
                            .userId(UUID.randomUUID().toString())
                            .createdDate(Instant.now())
                            .build())
                    .build();

            boolean auditSubmitted = assertDoesNotThrow(
                    () -> auditClient.submitContentSourceEvent(request));
            assertTrue(auditSubmitted);
        }

        @Disabled
        void testGetSASTokenUploadFile() throws JsonMapperException, HttpExecutionException, HttpResponseException {
            String aggregationId = "F0000004061";
            String itemId = UUID.randomUUID().toString();
            SubmitItemRequest itemRequest = SubmitItemRequest.Builder()
                    .setPayload(ItemSubmission.Builder()
                            .setExternalId(itemId)
                            .setConnectorId(CONNECTOR_ID)
                            .setTitle("Test item " + itemId)
                            .setAuthor("Connector Java SDK")
                            .setSourceLastModifiedDate(Instant.now())
                            .setSourceLastModifiedBy("Connector Java SDK")
                            .setSourceCreatedBy("Connector Java SDK")
                            .setSourceCreatedDate(Instant.now())
                            .setContentVersion("v1.0")
                            .setLocation("/test/file_test")
                            .setMediaType("Electronic")
                            .setParentExternalId(UUID.randomUUID().toString()) //Link to the item
                            .build())
                    .build();

            ItemAcceptance itemResponse = itemClient.submitItem(itemRequest);

            assertNotNull(itemResponse);

            GetBlobTokenResourceRequest request = GetBlobTokenResourceRequest.Builder()
                    .setPayload(DirectBinarySubmission.Builder()
                            .connectorId(CONNECTOR_ID)
                            .itemExternalId(itemResponse.getExternalId())
                            .binaryExternalId(UUID.randomUUID().toString())
                            .build())
                    .build();
            DirectBinarySubmissionOutput binaryResponse = binaryClient.getBlobToken(request);

            assertNotNull(binaryResponse);
        }

    }

    @Nested
    class PropertySourceTests {

        @Disabled
        void testPropertiesSourcesInAggregationSubmission() throws JsonMapperException, HttpExecutionException, HttpResponseException {
            String aggregationId = UUID.randomUUID().toString();
            SubmitAggregationRequest request = SubmitAggregationRequest.Builder()
                    .setPayload(getNewAggregationPayload(aggregationId)).build();
            assertTrue(aggregationClient.submitAggregation(request));
        }

        @Disabled
        void testPropertySourceInItemSubmission() throws JsonMapperException, HttpExecutionException, HttpResponseException {
            String aggregationId = "1b88ce8d-b97d-4415-8e1a-ace5b4152d39";
            String itemId = UUID.randomUUID().toString();
            SubmitItemRequest request = SubmitItemRequest.Builder()
                    .setPayload(getNewItemPayload(aggregationId, itemId)).build();

            ItemAcceptance response = itemClient.submitItem(request);

            assertNotNull(response);
        }

        private ItemSubmission getNewItemPayload(String aggregationId, String itemId) {
            return ItemSubmission.Builder()
                    .setSourceProperties(getMockPropertiesSource())
                    .setConnectorId(settings.getConnectorId())
                    .setExternalId(itemId)
                    .setParentExternalId(aggregationId)
                    .setTitle("Item " + itemId)
                    .setAuthor("Definity")
                    .setSourceCreatedBy("Definity")
                    .setSourceLastModifiedBy("Definity")
                    .setSourceCreatedDate(Instant.now())
                    .setSourceLastModifiedDate(Instant.now())
                    .setLocation("/users/definity/test")
                    .setMimeType("pdf")
                    .setContentVersion("1.0")
                    .setMediaType("Electronic")
                    .build();
        }

        private AggregationSubmission getNewAggregationPayload(String aggregationId) {
            return AggregationSubmission.Builder()
                    .setSourceProperties(getMockPropertiesSource())
                    .setConnectorId(settings.getConnectorId())
                    .setExternalId(aggregationId)
                    .setParentExternalId("")
                    .setTitle("Definity Test " + aggregationId)
                    .setAuthor("Definity")
                    .setLocation("/users/definity/test")
                    .setSourceCreatedBy("Definity")
                    .setSourceLastModifiedBy("Definity")
                    .setSourceLastModifiedDate(Instant.now())
                    .setSourceCreatedDate(Instant.now())
                    .build();
        }

        private List<Metadata> getMockPropertiesSource() {
            List<Metadata> properties = new ArrayList<>();
            properties.add(Metadata.Builder()
                    .setName("test metadata")
                    .setType("String")
                    .setValue("Demo")
                    .build());
            properties.add(Metadata.Builder()
                    .setName("another metadata")
                    .setType("String")
                    .setValue("Demo definity")
                    .build());
            return properties;
        }

    }

    @Nested
    class BinarySubmissionTests {

        @Disabled
        void testSubmitFiled() throws IOException, JsonMapperException, HttpExecutionException, HttpResponseException {
            String itemId = "cba51802-7e9b-41b7-a138-c86cf69815a3";
            String fileId = UUID.randomUUID().toString();
            String fileTestPath = getClass().getClassLoader().getResource("files/test.pdf").getPath();
            //Use the stream of the data
            DirectBinarySubmission fileSubmission = DirectBinarySubmission.Builder()
                    .connectorId(settings.getConnectorId())
                    .binaryExternalId(fileId)
                    .itemExternalId(itemId)
                    .fileName("test.pdf")
                    .fileSize(33000L).build();

            assertTrue(binaryClient.submitFile(
                    SubmitFileRequest.Builder()
                            .setFileSubmissionInfo(fileSubmission)
                            .setFileContent(Files.newInputStream(Paths.get(fileTestPath))).build()));

        }

    }
}
