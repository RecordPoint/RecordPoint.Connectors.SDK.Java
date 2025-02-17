package com.recordpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "notifications")
public class NotificationCommand extends BaseCommand implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(NotificationCommand.class);

    public void run() {
        LOG.info("Checking for new notifications");

        ServiceSettings settings = getServiceSettings();

        try (TokenManager tokenManager = new MsalTokenManager(settings)) {
            NotificationServiceClient notificationServiceClient = NotificationServiceClient.Builder()
                    .setServiceSettings(settings)
                    .setTokenManager(tokenManager)
                    .build();

            List<Notification> notifications = notificationServiceClient.getNotificationList(GetNotificationRequest.Builder()
                    .setConnectorId(connectorId)
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
                                .setConnectorId(connectorId)
                                .setNotificationId(notification.getId())
                                .setConnectorStatusMessage("Item destroyed using Java SDK!")
                                .setProcessingResult(NotificationAcknowledge.ProcessingResult.OK)
                                .build()
                        )
                        .build()
                );
            }

        } catch (Exception exc) {
            LOG.error("Unable to process notifications", exc);
        }
    }
}
