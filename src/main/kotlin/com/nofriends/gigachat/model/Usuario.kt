package com.nofriends.gigachat.model

import com.nofriends.gigachat.config.TimeSerializer
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Column
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import jakarta.persistence.Transient
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

@Serializable
@Entity
@Table(name = "usuario", schema = "public")
class Usuario (
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario")
  var id: Int = 0,

  @Column(name = "username", unique = true, nullable = false, length = 50)
  var username: String = "",

  @Transient
  @Column(name = "password", nullable = false, length = 256)
  var password: String? = null,

  @Column(name = "email", length = 100)
  var email: String? = null,

  @Column(name = "fecha_registro", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  @Serializable(with = TimeSerializer::class)
  var fechaRegistro: ZonedDateTime? = null,

  @Enumerated(EnumType.STRING)
  @Column(name = "estado", nullable = false)
  var estado: Estado = Estado.undefined
){
  override fun toString(): String {
    return "Usuario: {" +
        "id: ${this.id}, " +
        "username: ${this.username}, " +
        "password: ${this.password}, " +
        "email: ${this.email}, " +
        "fechaRegistro: ${this.fechaRegistro}, " +
        "estado: ${this.estado}" +
        "}"
  }
}