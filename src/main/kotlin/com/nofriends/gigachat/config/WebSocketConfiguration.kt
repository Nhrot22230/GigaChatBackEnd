package com.nofriends.gigachat.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.StompEndpointRegistry

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfiguration : WebSocketMessageBrokerConfigurer {
  @Autowired
  private lateinit var loggingInterceptor: LoggingInterceptor

  override fun configureMessageBroker(config: MessageBrokerRegistry) {
    config.enableSimpleBroker("/topic", "/queue")
    config.setApplicationDestinationPrefixes("/app")
  }

  override fun registerStompEndpoints(registry: StompEndpointRegistry) {
    registry
      .addEndpoint("/ws")
      .setAllowedOriginPatterns("*")
      .withSockJS()
  }

  override fun configureClientInboundChannel(registration: ChannelRegistration) {
    registration.interceptors(loggingInterceptor)
  }
}
