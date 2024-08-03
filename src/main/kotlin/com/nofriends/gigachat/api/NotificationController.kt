package com.nofriends.gigachat.api

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import com.nofriends.gigachat.dto.UserNotification
import org.springframework.messaging.handler.annotation.DestinationVariable


@Controller
class NotificationController() {
  @MessageMapping("/notify/{userId}")
  @SendTo("/queue/notification/{userId}")
  fun sendNotification(@DestinationVariable userId: String, userNotification: UserNotification): UserNotification {
    println("Sending $userNotification to user $userId")

    return userNotification
  }
}
