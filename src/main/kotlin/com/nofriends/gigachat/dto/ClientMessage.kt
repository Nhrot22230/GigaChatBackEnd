package com.nofriends.gigachat.dto
import com.nofriends.gigachat.model.Usuario
import kotlinx.serialization.Serializable

@Serializable
data class ClientMessage(
  val chatId: Int,
  val usuario: Usuario,
  val type: String?=null,
  val content: String?=null,
  val timestamp: String?=null
)