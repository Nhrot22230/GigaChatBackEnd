package com.gigachat.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
  val userid: Long,
  val nickname: String,
  val password: String? = null,
  val firstName: String,
  val lastName: String,
  var friends: Map<Long, User> = emptyMap()
){
  override fun toString(): String {
    var message = "Hello my name is $firstName $lastName [$nickname]"

    if (friends.isNotEmpty()) {
      message += "\n\tFriends:"
      friends.forEach{ message += "\t${it.value.nickname}\n" }
    }

    return message
  }
}