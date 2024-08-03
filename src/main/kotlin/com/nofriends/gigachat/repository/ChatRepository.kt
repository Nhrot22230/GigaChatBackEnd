package com.nofriends.gigachat.repository

import com.nofriends.gigachat.model.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.time.Instant
import java.time.ZonedDateTime

@Repository
interface ChatRepository : JpaRepository<Chat, Int> {

  @Query(
    value = "SELECT * FROM Obtener_chats_usuario_mensaje(:p_id_usuario)",
    nativeQuery = true
  )
  fun obtenerChatsUsuarioMensaje(@Param("p_id_usuario") idUsuario: Int): List<Array<Any>>

  @Query(
    value = "SELECT * FROM ObtenerChatsXUsuarios(:p_id_usuario)",
    nativeQuery = true
  )
  fun obtenerChatsXUsuarios(@Param("p_id_usuario") idUsuario: Int): List<Array<Any>>

  @Query(
    value = "SELECT AgregarUsuarioChat(:p_id_chat, :p_id_usuario)",
    nativeQuery = true
  )
  fun agregarUsuarioChat(@Param("p_id_chat") idChat: Int, @Param("p_id_usuario") idUsuario: Int)

  @Query(
    value = "SELECT EliminarUsuarioChat(:p_id_chat, :p_id_usuario)",
    nativeQuery = true
  )
  fun eliminarUsuarioChat(@Param("p_id_chat") idChat: Int, @Param("p_id_usuario") idUsuario: Int)

  @Query(
    value = "SELECT ActualizarUsuarioChat(:p_id_chat, :p_id_usuario, :p_rol)",
    nativeQuery = true
  )
  fun actualizarUsuarioChat(@Param("p_id_chat") idChat: Int, @Param("p_id_usuario") idUsuario: Int, @Param("p_rol") rol: String)

  @Query(
    value = "SELECT GuardarMensaje(:p_id_chat, :p_id_usuario, :p_contenido, :p_tipo)",
    nativeQuery = true
  )
  fun guardarMensaje(
    @Param("p_id_chat") idChat: Int,
    @Param("p_id_usuario") idUsuario: Int,
    @Param("p_contenido") contenido: String,
    @Param("p_tipo") tipo: String
  )
}