package com.nofriends.gigachat.dto

import com.nofriends.gigachat.model.Usuario
import kotlinx.serialization.Serializable

@Serializable
data class ChatDTO(
  var id: Int,
  val titulo: String,
  val descripcion: String?,
  val fechaCreacion: String,
  val tipo: String,
  val imagen: String?,
  val usuarios: List<Usuario>
)