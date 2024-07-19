package com.gigachat.config

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import org.springframework.stereotype.Service

@Service
object DBManager {
  private const val HOST = "localhost"
  private const val PORT = 3306
  private const val USER = "root"
  private const val PASSWORD = "123"
  private const val DATABASE = "ChatP3" // replace with your database name
  private var URL = "jdbc:mariadb://$HOST:$PORT/$DATABASE"

  fun getConnection(): Connection? {
    return try {
      DriverManager.getConnection(URL, USER, PASSWORD)
    } catch (ex: SQLException) {
      ex.printStackTrace()
      null
    }
  }
}