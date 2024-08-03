package com.nofriends.gigachat.config

import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
class LoggingInterceptor : ChannelInterceptor {

  private val logger = LoggerFactory.getLogger(LoggingInterceptor::class.java)

  override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
    val accessor = StompHeaderAccessor.wrap(message)
    when (accessor.command) {
      StompCommand.CONNECT -> logger.info("Nueva conexión entrante: ${accessor.sessionId}")
      StompCommand.DISCONNECT -> logger.info("Conexión terminada: ${accessor.sessionId}")
      StompCommand.SEND -> logger.info("Mensaje recibido: ${message.payload}")
      StompCommand.SUBSCRIBE -> logger.info("Cliente suscrito a: ${accessor.destination}")
      StompCommand.UNSUBSCRIBE -> logger.info("Cliente desuscrito de: ${accessor.destination}")
      else -> logger.info("Evento STOMP: ${accessor.command}")
    }
    return message
  }
}
