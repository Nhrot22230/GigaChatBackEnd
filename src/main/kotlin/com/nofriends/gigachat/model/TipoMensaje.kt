package com.nofriends.gigachat.model
import kotlinx.serialization.Serializable

@Serializable
enum class TipoMensaje {
  undefined,
  texto,
  imagen,
  archivo
}