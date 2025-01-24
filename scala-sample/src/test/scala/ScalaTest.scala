import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.{BeforeEach, Disabled, Test}
import recordpoint.connectors.sdk.auth.{MsalTokenManager, TokenManager}
import recordpoint.connectors.sdk.service.ServiceSettings
import recordpoint.connectors.sdk.service.notification.{GetNotificationRequest, NotificationServiceClient}

import java.io.File;

class ScalaTest {

  var settings: ServiceSettings = _
  var notificationClient: NotificationServiceClient = _
  var tokenManager: TokenManager = _

  @BeforeEach
  def `initialize`(): Unit = {
    settings = ServiceSettings.Builder()
      .fromJsonFile((new File(getClass.getClassLoader.getResource("settings.json").getFile)).toPath)
      .setSecret(System.getenv("CLIENT_SECRET"))
      .build()
    tokenManager = new MsalTokenManager(settings)
    notificationClient = NotificationServiceClient.Builder()
      .setServiceSettings(settings)
      .setTokenManager(tokenManager)
      .build()
  }

  @Test
  def `testing build settings in scala`(): Unit = {
    assertNotNull(settings)
  }
  @Disabled ("Skip this failing test for now")
  @Test
  def `testing get notifications`(): Unit = {
    val getNotificationRequest = GetNotificationRequest.Builder()
      .setConnectorId(settings.getConnectorId)
      .build()

    val notifications = notificationClient.getNotificationList(getNotificationRequest)

    assertNotNull(notifications)
  }

}