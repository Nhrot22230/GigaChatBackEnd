package com.gigachat.api

import com.gigachat.interfaces.IUserMapper
import com.gigachat.mappers.UserMapper
import com.gigachat.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/giga-chat/users")
class UserController {
  private val dbReader: IUserMapper = UserMapper()

  @GetMapping("/sayHello")
  @CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
  fun sayHello(): String {
    return "Hello, Kotlin with Spring Boot!"
  }

  @GetMapping("/sayUsers")
  @CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
  fun sayUsers(@RequestParam(required = false, defaultValue = "") search: String): String {
    val users = dbReader.listUsers(search)
    var msg = ""
    users.forEach {
      msg += it.toString() + "\n"
    }

    return msg
  }

  @GetMapping("/listUsers")
  @CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
  fun listUsers(@RequestParam(required = false, defaultValue = "") search: String): ResponseEntity<List<User>> {
    val users: List<User> = dbReader.listUsers(search)
    return if (users.isNotEmpty()) {
      ResponseEntity(users, HttpStatus.OK)
    } else {
      ResponseEntity(emptyList(), HttpStatus.NOT_FOUND)
    }
  }

  @GetMapping("/login")
  @CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
  fun login(@RequestParam(required = true, defaultValue = "") nickname: String, @RequestParam(required = true, defaultValue = "") passwd: String): ResponseEntity<User> {

    return ResponseEntity(null, HttpStatus.NOT_FOUND)
  }
}
