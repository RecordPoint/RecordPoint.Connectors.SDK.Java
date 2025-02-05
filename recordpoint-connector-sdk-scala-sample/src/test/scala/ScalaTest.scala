import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.{BeforeEach, Test}
import com.recordpoint.connectors.sdk.auth.{MsalTokenManager, TokenManager}
import com.recordpoint.connectors.sdk.service.ServiceSettings
import com.recordpoint.connectors.sdk.service.notification.{GetNotificationRequest, NotificationServiceClient}

import java.io.File;

class ScalaTest {

  var settings: ServiceSettings = _
  var notificationClient: NotificationServiceClient = _
  var tokenManager: TokenManager = _

  @BeforeEach
  def `initialize`(): Unit = {
    assertNotNull(System.getenv("CLIENT_ID"), "Environment variable 'CLIENT_ID' is not set")
    assertNotNull(System.getenv("CLIENT_SECRET"), "Environment variable 'CLIENT_SECRET' is not set")

    settings = ServiceSettings.Builder()
      .fromJsonFile((new File(getClass.getClassLoader.getResource("settings.json").getFile)).toPath)
      .setClientId(System.getenv("CLIENT_ID"))
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

  @Test
  def `testing get notifications`(): Unit = {
    val getNotificationRequest = GetNotificationRequest.Builder()
      .setConnectorId(settings.getConnectorId)
      .build()

    val notifications = notificationClient.getNotificationList(getNotificationRequest)

    assertNotNull(notifications)
  }

}