package recordpoint.connectors.sdk.service.notification;

import recordpoint.connectors.sdk.http.exception.HttpExecutionException;
import recordpoint.connectors.sdk.http.exception.HttpResponseException;
import recordpoint.connectors.sdk.json.JsonMapperException;
import recordpoint.connectors.sdk.service.AbstractServiceClient;
import recordpoint.connectors.sdk.service.ServiceSettings;
import recordpoint.connectors.sdk.service.notification.model.Notification;

import java.util.List;

public final class NotificationServiceClient extends AbstractServiceClient {

    public NotificationServiceClient(Builder builder) {
        super(builder);
    }

    public static Builder Builder() {
        return new Builder();
    }

    public List<Notification> getNotificationList(GetNotificationRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s?connectorId=%s", getRootUrl(), request.getServicePath(), request.getConnectorId());
        return getRequestList(resourceUrl, Notification.class);
    }

    public boolean acknowledgeProcessedNotification(AcknowledgeNotificationRequest request) throws JsonMapperException, HttpExecutionException, HttpResponseException {
        String resourceUrl = String.format("%s%s", getRootUrl(), request.getServicePath());
        postRequest(resourceUrl, request.getPayload());
        return true;
    }

    public static class Builder extends AbstractServiceClient.Builder {

        @Override
        public NotificationServiceClient build() {
            return new NotificationServiceClient(this);
        }

        @Override
        public NotificationServiceClient.Builder setServiceSettings(ServiceSettings serviceSettings) {
            super.setServiceSettings(serviceSettings);
            return this;
        }

    }

}
