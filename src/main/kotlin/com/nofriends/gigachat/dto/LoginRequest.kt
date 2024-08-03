package com.nofriends.gigachat.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
  val username: String,
  val password: String
)