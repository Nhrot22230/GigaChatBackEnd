package com.nofriends.gigachat.api

import com.nofriends.gigachat.dto.ChatDTO
import com.nofriends.gigachat.dto.ClientMessage
import com.nofriends.gigachat.model.Chat
import com.nofriends.gigachat.model.TipoChat
import com.nofriends.gigachat.repository.UsuarioRepository
import com.nofriends.gigachat.service.ChatService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestBody
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/api/chats")
class ChatController(private val chatService: ChatService, private val usuarioRepository: UsuarioRepository) {

  @GetMapping
  @Transactional(readOnly = true)
  fun getChatsByUser(@RequestParam userId: Int): ResponseEntity<List<ChatDTO>> {
    usuarioRepository.findByIdOrNull(userId) ?: return ResponseEntity.notFound().build()

    val chats = chatService.obtenerChatsPorUsuario(userId)
    return ResponseEntity.ok(chats)
  }

  @GetMapping("/messages")
  @Transactional(readOnly = true)
  fun getMessagesFromUserChats(@RequestParam userId: Int) : ResponseEntity<List<ClientMessage>> {
    usuarioRepository.findByIdOrNull(userId) ?: return ResponseEntity.notFound().build()

    val messages = chatService.obtenerMensajesChats(userId)

    return ResponseEntity.ok(messages)
  }

  @PostMapping("/{chatId}/usuarios")
  fun addUserToChat(@PathVariable chatId: Int, @RequestParam userId: Int): ResponseEntity<Void> {
    chatService.findByIdOrNull(chatId) ?: return ResponseEntity.notFound().build()

    return try {
      chatService.agregarUsuarioChat(chatId, userId)
      ResponseEntity.status(HttpStatus.CREATED).build()
    } catch (ex: Exception) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
  }

  @DeleteMapping("/{chatId}/usuarios")
  fun removeUserFromChat(@PathVariable chatId: Int, @RequestParam userId: Int): ResponseEntity<Void> {
    chatService.findByIdOrNull(chatId) ?: return ResponseEntity.notFound().build()

    return try {
      chatService.eliminarUsuarioChat(chatId, userId)
      ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    } catch (ex: Exception) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
  }

  @PutMapping("/{chatId}/usuarios")
  fun updateUserRoleInChat(@PathVariable chatId: Int, @RequestParam userId: Int, @RequestParam role: String): ResponseEntity<Void> {
    chatService.findByIdOrNull(chatId) ?: return ResponseEntity.notFound().build()

    return try {
      chatService.actualizarUsuarioChat(chatId, userId, role)
      ResponseEntity.ok().build()
    } catch (ex: Exception) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
  }

  @PostMapping
  fun createChat(@RequestBody chat: ChatDTO): ResponseEntity<ChatDTO> {
    return try {
      println("Creating chat: $chat")
      val savedChat = chatService.save(
        Chat(
          id = chat.id,
          titulo = chat.titulo,
          tipo = TipoChat.valueOf(chat.tipo),
          fechaCreacion = ZonedDateTime.parse(chat.fechaCreacion, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
          descripcion = chat.descripcion,
          imagen = chat.imagen,
        )
      )
      println("Saved Chat: $savedChat")

      chat.usuarios.forEach{
        usuario -> chatService.agregarUsuarioChat(savedChat.id, usuario.id)
      }
      chat.id = savedChat.id
      ResponseEntity.status(HttpStatus.CREATED).body(chat)
    } catch (ex: Exception) {
      println("Error: ${ex.message}")
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
  }

  @GetMapping("/{chatId}")
  fun getChatById(@PathVariable chatId: Int): ResponseEntity<Chat> {
    val chat = chatService.findByIdOrNull(chatId) ?: return ResponseEntity.notFound().build()

    return ResponseEntity.ok(chat)
  }

  @PutMapping("/{chatId}")
  fun updateChat(@PathVariable chatId: Int, @RequestBody chatDetails: Chat): ResponseEntity<Chat> {
    val chat = chatService.findByIdOrNull(chatId) ?: return ResponseEntity.notFound().build()
    return try {
      chat.titulo = chatDetails.titulo
      chat.descripcion = chatDetails.descripcion
      chat.fechaCreacion = chatDetails.fechaCreacion
      chat.tipo = chatDetails.tipo
      chat.imagen = chatDetails.imagen
      val updatedChat = chatService.save(chat)
      ResponseEntity.ok(updatedChat)
    } catch (ex: Exception) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
  }

  @DeleteMapping("/{chatId}")
  fun deleteChat(@PathVariable chatId: Int): ResponseEntity<Void> {
    return try {
      chatService.deleteById(chatId)
      ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    } catch (ex: Exception) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }
  }
}