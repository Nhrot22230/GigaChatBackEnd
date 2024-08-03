package com.nofriends.gigachat.dto
import kotlinx.serialization.Serializable

@Serializable
data class UserNotification(
  val chat: ChatDTO,
  val messages: List<ClientMessage>,
)
