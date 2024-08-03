package com.nofriends.gigachat.model
import kotlinx.serialization.Serializable

@Serializable
enum class TipoChat {
  undefined,
  individual,
  grupal,
}