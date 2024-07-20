package com.gigachat.mappers

import com.gigachat.config.DBManager
import java.sql.Connection
import java.sql.ResultSet
import java.sql.CallableStatement
import java.sql.SQLException
import com.gigachat.model.User
import com.gigachat.interfaces.IUserMapper

class UserMapper : IUserMapper {

  override fun listUsers(s: String): ArrayList<User> {
    val users = ArrayList<User>()
    try {
      val con:Connection = DBManager.getConnection()!!
      val sql = "{ CALL ListUsers(?) }"
      val cs:CallableStatement = con.prepareCall(sql)
      cs.setString(1, s)
      val rs:ResultSet = cs.executeQuery()
      while (rs.next()) {
        val user = User(
          userid = rs.getLong("userid"),
          nickname = rs.getString("nickname"),
          password = null,
          firstName = rs.getString("firstName"),
          lastName = rs.getString("lastName"),
        )
        users.add(user)
      }
    } catch (ex: SQLException) {
      println("SQL Exception: ${ex.message}")
    }
    return users
  }

  override fun login(username: String, password: String): User? {
    try{
      val con:Connection = DBManager.getConnection()!!
      val sql = "{ CALL LogUserIn(?,?) }"
      val cs:CallableStatement = con.prepareCall(sql)
      cs.setString(1, username)
      cs.setString(2, password)
      val rs:ResultSet = cs.executeQuery()
      var user: User? = null
      if (rs.next()) {
        user = User(
          userid = rs.getLong("userid"),
          nickname = rs.getString("nickname"),
          firstName = rs.getString("firstName"),
          lastName = rs.getString("lastName")
        )
      }

      return user
    }
    catch (ex: SQLException) {
      println("SQL Exception: ${ex.message}")
    }

    return null
  }
}