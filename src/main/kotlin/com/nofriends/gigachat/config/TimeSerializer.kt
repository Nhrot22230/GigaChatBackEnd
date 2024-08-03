package com.nofriends.gigachat.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = ZonedDateTime::class)
class TimeSerializer : KSerializer<ZonedDateTime> {
  private val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME

  override fun serialize(encoder: Encoder, value: ZonedDateTime) {
    val string = value.format(formatter)
    encoder.encodeString(string)
  }

  override fun deserialize(decoder: Decoder): ZonedDateTime {
    val string = decoder.decodeString()

    return try {
      ZonedDateTime.parse(string, formatter)
    } catch (e: DateTimeParseException) {
      println("Error Parser: ${e.message}")
      ZonedDateTime.now()
    }
  }
}
