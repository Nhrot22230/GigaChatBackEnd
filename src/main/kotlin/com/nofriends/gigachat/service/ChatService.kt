package com.nofriends.gigachat.service

import com.nofriends.gigachat.dto.ChatDTO
import com.nofriends.gigachat.dto.ClientMessage
import com.nofriends.gigachat.model.Chat
import com.nofriends.gigachat.model.Estado
import com.nofriends.gigachat.model.Usuario
import com.nofriends.gigachat.repository.ChatRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class ChatService {
  @Autowired
  private lateinit var rawChatRepository: ChatRepository

  fun obtenerChatsPorUsuario(idUsuario: Int): List<ChatDTO> {
    val rawResults = rawChatRepository.obtenerChatsXUsuarios(idUsuario)

    val chatMap = mutableMapOf<Int, ChatDTO>()

    for (result in rawResults) {
      val chatId = result[0] as Int
      val chatTitulo = result[1] as String
      val chatDescripcion = result[2] as String?
      val chatFechaCreacion = result[3] as Instant
      val chatTipo = result[4] as String
      val chatImagen = result[5] as String?
      val usuarioId = result[6] as Int
      val usuarioUsername = result[7] as String
      val usuarioEmail = result[8] as String?
      val usuarioFechaRegistro = result[9] as Instant
      val usuarioEstado = Estado.valueOf(result[10] as String)

      val usuarioDTO = Usuario(
        id = usuarioId,
        username = usuarioUsername,
        email = usuarioEmail,
        fechaRegistro = ZonedDateTime.ofInstant(usuarioFechaRegistro, ZoneId.of("America/Lima")),
        estado = usuarioEstado,
        password = null
      )

      val chatDTO = chatMap[chatId] ?: ChatDTO(
        id = chatId,
        titulo = chatTitulo,
        descripcion = chatDescripcion,
        fechaCreacion = ZonedDateTime.ofInstant(chatFechaCreacion, ZoneId.of("America/Lima")).toString(),
        tipo = chatTipo,
        imagen = chatImagen,
        usuarios = mutableListOf()
      )

      (chatDTO.usuarios as MutableList).add(usuarioDTO)
      chatMap[chatId] = chatDTO
    }

    return chatMap.values.toList()
  }

  fun obtenerMensajesChats(idUsuario: Int): List<ClientMessage> {
    val rawResults = rawChatRepository.obtenerChatsUsuarioMensaje(idUsuario)

    val mapMessages: MutableMap<Int, MutableList<ClientMessage>> = mutableMapOf()

    for (result in rawResults) {
      val idChat = result[0] as Int
      val fechaHora = result[1] as Instant
      val contenido = result[2] as String
      val tipo = result[3] as String
      val usuarioId = result[4] as Int
      val username = result[5] as String
      val email = result[6] as String
      val fechaRegistro = result[7] as Instant
      val estado = Estado.valueOf(result[8] as String)

      val clientMessage = ClientMessage(
        chatId = idChat,
        usuario = Usuario(
          id = usuarioId,
          username = username,
          email = email,
          fechaRegistro = ZonedDateTime.ofInstant(fechaRegistro, ZoneId.of("America/Lima")),
          estado = estado
        ),
        type = tipo,
        content = contenido,
        timestamp = fechaHora.toString()
      )

      if (mapMessages[idChat] == null) {
        mapMessages[idChat] = mutableListOf()
      }
      mapMessages[idChat]?.add(clientMessage)
    }

    val clientMessages = mutableListOf<ClientMessage>()
    for ((_, messages) in mapMessages) {
      clientMessages.addAll(messages)
    }

    return clientMessages
  }

  fun agregarUsuarioChat(idChat: Int, idUsuario: Int) = rawChatRepository.agregarUsuarioChat(idChat, idUsuario)

  fun eliminarUsuarioChat(idChat: Int, idUsuario: Int) = rawChatRepository.eliminarUsuarioChat(idChat, idUsuario)

  fun actualizarUsuarioChat(idChat: Int, idUsuario: Int, rol: String) = rawChatRepository.actualizarUsuarioChat(idChat, idUsuario, rol)

  fun findByIdOrNull(id: Int): Chat? = rawChatRepository.findByIdOrNull(id)

  fun deleteById(id: Int) = rawChatRepository.deleteById(id)

  fun save(chat: Chat) = rawChatRepository.save(chat)

  fun guardarMensaje(idChat: Int, idUsuario: Int, contenido: String, tipo: String) {
    rawChatRepository.guardarMensaje(idChat, idUsuario, contenido, tipo)
  }
}
