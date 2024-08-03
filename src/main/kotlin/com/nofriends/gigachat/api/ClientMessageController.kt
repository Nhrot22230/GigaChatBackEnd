package com.nofriends.gigachat.api

import com.nofriends.gigachat.dto.ClientMessage
import com.nofriends.gigachat.model.TipoMensaje
import com.nofriends.gigachat.service.ChatService
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Controller
class ClientMessageController(private val chatService: ChatService) {

  private val dateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME

  @MessageMapping("/chat/send/{chatId}")
  @SendTo("/topic/chat/{chatId}")
  fun sendMessage(@DestinationVariable chatId: String, message: ClientMessage): ClientMessage {
    val dateTime = parseTimestamp(message.timestamp)

    try {
      println("la fecha hora: $dateTime")
      chatService.guardarMensaje(
        idChat = message.chatId,
        idUsuario = message.usuario.id,
        contenido = message.content ?: "",
        tipo = try { TipoMensaje.valueOf(message.type?: "").toString() } catch (e: Exception) { TipoMensaje.texto.toString() }
      )
    } catch (e: Exception) {
      println("Error al guardar el mensaje: ${e.message}")
    }

    return message.copy(
      chatId = message.chatId,
      usuario = message.usuario
    )
  }

  private fun parseTimestamp(timestamp: String?): ZonedDateTime {
    return try {
      ZonedDateTime.parse(timestamp?: "", dateTimeFormatter)
    } catch (e: DateTimeParseException) {
      ZonedDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.of("America/Lima"))
    }
  }
}
