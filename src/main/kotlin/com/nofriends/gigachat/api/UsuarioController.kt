package com.nofriends.gigachat.api

import com.nofriends.gigachat.dto.LoginRequest
import com.nofriends.gigachat.model.Estado
import com.nofriends.gigachat.model.Usuario
import com.nofriends.gigachat.service.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestBody

@RestController
@RequestMapping("/api/usuarios")
class UsuarioController(private val usuarioRepository: UsuarioService) {
  @GetMapping("/{id}")
  fun getUsuarioById(@PathVariable id: Int): ResponseEntity<Usuario> {
    val usuario = usuarioRepository.findById(id)
    if (usuario != null ) return ResponseEntity.ok(usuario)

    return ResponseEntity.notFound().build()
  }

  @GetMapping
  fun getUsuariosBySearch(@RequestParam("search") keyword: String): ResponseEntity<List<Usuario>> = ResponseEntity.ok(usuarioRepository.buscarUsuariosPorUsername(keyword))

  @PostMapping("/login")
  @Transactional(readOnly = true )
  fun loginUsuario(@RequestBody loginRequest: LoginRequest): ResponseEntity<Usuario> {
    return try {
      val usuario: Usuario? = usuarioRepository.iniciarSesion(loginRequest.username, loginRequest.password)
      println(usuario)
      ResponseEntity.ok(usuario)
    } catch (e: Exception) {
      println(e.message)

      ResponseEntity.status(HttpStatus.FORBIDDEN).build()
    }
  }

  @PostMapping
  fun createUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {

    return try {
      val returnedId = usuarioRepository.registrarUsuario(
        username = usuario.username,
        password = usuario.password?: "",
        email = usuario.email?: "",
        estado = if (usuario.estado == Estado.undefined) Estado.activo.toString() else usuario.estado.toString(),
      )
      usuario.id = returnedId
      ResponseEntity.ok(usuario)
    }
    catch (e: Exception) {
      println(e.message)
      ResponseEntity.status(HttpStatus.FORBIDDEN).build()
    }
  }

  @PutMapping("/{id}")
  fun updateUsuario(@PathVariable id: Int, @RequestBody usuario: Usuario): ResponseEntity<Usuario> {
    return try {
      usuarioRepository.actualizarUsuario(
        idUsuario = id,
        username = usuario.username,
        password = usuario.password?: "",
        email = usuario.email?: "",
        estado = usuario.estado.toString()
      )
      ResponseEntity.ok(usuario)
    }
    catch (e: Exception) {
      println(e.message)
      ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }
  }

  @DeleteMapping("/{id}")
  fun deleteUsuario(@PathVariable id: Int): ResponseEntity<Void> {
    return try {
      usuarioRepository.eliminarUsuario(idUsuario = id)
      ResponseEntity.noContent().build()
    }
    catch (e: Exception) {
      println(e.message)
      ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }
  }
}
