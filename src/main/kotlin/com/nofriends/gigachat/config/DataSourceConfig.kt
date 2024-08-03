package com.nofriends.gigachat.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.core.env.Environment
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

  @Value("\${spring.datasource.url}")
  private lateinit var dbUrl: String

  @Value("\${spring.datasource.username}")
  private lateinit var dbUsername: String

  @Value("\${spring.datasource.password}")
  private lateinit var dbPassword: String

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  fun dataSource(): DataSource {
    return DataSourceBuilder.create()
      .url(dbUrl)
      .username(dbUsername)
      .password(dbPassword)
      .build()
  }
}
