package com.nofriends.gigachat.dto
import com.nofriends.gigachat.config.TimeSerializer
import com.nofriends.gigachat.model.Estado
import kotlinx.serialization.Serializable

@Serializable
data class UsuarioDTO(
  val id: Int,
  val username: String,
  val password: String,
  val email: String?=null,
  @Serializable(with = TimeSerializer::class)
  val fechaRegistro: String? = null,
  val estado: Estado = Estado.undefined
)