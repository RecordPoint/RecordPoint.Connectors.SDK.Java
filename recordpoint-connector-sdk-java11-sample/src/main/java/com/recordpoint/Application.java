package com.recordpoint;

import com.recordpoint.connectors.sdk.auth.MsalTokenManager;
import com.recordpoint.connectors.sdk.auth.TokenManager;
import com.recordpoint.connectors.sdk.service.ServiceSettings;
import com.recordpoint.connectors.sdk.service.notification.AcknowledgeNotificationRequest;
import com.recordpoint.connectors.sdk.service.notification.GetNotificationRequest;
import com.recordpoint.connectors.sdk.service.notification.NotificationServiceClient;
import com.recordpoint.connectors.sdk.service.notification.model.Notification;
import com.recordpoint.connectors.sdk.service.notification.model.NotificationAcknowledge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOG.info("Starting Application v11");

        ServiceSettings.Builder builder = ServiceSettings.Builder()
                .setConnectorId(System.getenv("CONNECTOR_ID"))
                .setTenantId(System.getenv("TENANT_ID"))
                .setClientId(System.getenv("CLIENT_ID"))
                .setRegion(ServiceSettings.Regions.CAC)
                .setSecret(System.getenv("CLIENT_SECRET"));

        try (TokenManager tokenManager = new MsalTokenManager(builder.build())) {
            NotificationServiceClient notificationServiceClient = NotificationServiceClient.Builder()
                    .setServiceSettings(builder.build())
                    .setTokenManager(tokenManager)
                    .build();

            List<Notification> notifications = notificationServiceClient.getNotificationList(GetNotificationRequest.Builder()
                    .setConnectorId(System.getenv("CONNECTOR_ID"))
                    .build()
            );

            for (Notification notification : notifications) {
                LOG.info("Found notification: type={}; id={}", notification.getNotificationType(), notification.getId());

                if (!Notification.NotificationType.ITEM_DESTROYED.equals(notification.getNotificationType())) {
                    LOG.info("Skipping notification as not ItemDestroyed event");
                    continue;
                }

                LOG.info(
                        "Deleting item: externalID={}; title={}; author={}, sourceProperties={}",
                        notification.getItem().getExternalId(), notification.getItem().getTitle(),
                        notification.getItem().getAuthor(), notification.getItem().getSourceProperties()
                );

                // Perform deletion here

                notificationServiceClient.acknowledgeProcessedNotification(AcknowledgeNotificationRequest.Builder()
                        .setNotificationAcknowledge(NotificationAcknowledge.Builder()
                                .setConnectorId(System.getenv("CONNECTOR_ID"))
                                .setNotificationId(notification.getId())
                                .setConnectorStatusMessage("Item destroyed using Java SDK!")
                                .setProcessingResult(NotificationAcknowledge.ProcessingResult.OK)
                                .build()
                        )
                        .build()
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
