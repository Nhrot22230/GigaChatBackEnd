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
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

@Serializable
@Entity
@Table(name = "Chat", schema = "public")
class Chat (
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_chat", columnDefinition = "INT")
  var id: Int = 0,

  @Column(name = "titulo", columnDefinition = "VARCHAR", length = 100, nullable = false)
  var titulo: String = "",

  @Column(name = "descripcion", columnDefinition = "VARCHAR", length = 100)
  var descripcion: String?= null,

  @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false)
  @Serializable(with = TimeSerializer::class)
  var fechaCreacion: ZonedDateTime? = null,

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo", nullable = false)
  var tipo: TipoChat = TipoChat.undefined,

  @Column(name = "imagen", columnDefinition = "VARCHAR", length = 256)
  var imagen: String?=null
) {

  override fun toString(): String {
    return "Chat: {" +
        "id: ${this.id}, " +
        "titulo: ${this.titulo}, " +
        "descripcion: ${this.descripcion}, " +
        "fecha_creacion: ${this.fechaCreacion}, " +
        "tipo: ${this.tipo}, " +
        "imagen: ${this.imagen}" +
        "}"
  }
}
