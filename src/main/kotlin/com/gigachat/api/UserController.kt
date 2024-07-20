package com.gigachat.api

import com.gigachat.dto.LoginRequest
import com.gigachat.interfaces.IUserMapper
import com.gigachat.mappers.UserMapper
import com.gigachat.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

  @PostMapping("/login")
  @CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
  fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<User> {
    val nickname = loginRequest.nickname
    val passwd = loginRequest.password

    val user = dbReader.login(nickname, passwd)

    return if (user != null) {
      ResponseEntity(user, HttpStatus.OK)
    }
    else {
      ResponseEntity(null, HttpStatus.NOT_FOUND)
    }
  }
}
