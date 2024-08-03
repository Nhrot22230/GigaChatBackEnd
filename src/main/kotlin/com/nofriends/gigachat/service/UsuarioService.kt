package com.nofriends.gigachat.service

import com.nofriends.gigachat.model.Usuario
import com.nofriends.gigachat.repository.UsuarioRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UsuarioService(private val usuarioRepository: UsuarioRepository) {

  @Transactional
  fun registrarUsuario(username: String, password: String, email: String, estado: String?): Int {
    return usuarioRepository.registrarUsuario(username, password, email, estado)
  }

  @Transactional
  fun actualizarUsuario(idUsuario: Int, username: String, password: String, email: String, estado: String) {
    usuarioRepository.actualizarUsuario(idUsuario, username, password, email, estado)
  }

  @Transactional
  fun eliminarUsuario(idUsuario: Int) {
    usuarioRepository.eliminarUsuario(idUsuario)
  }

  fun iniciarSesion(username: String, password: String): Usuario? {
    return usuarioRepository.iniciarSesion(username, password)
  }

  fun buscarUsuariosPorUsername(query: String): List<Usuario> {
    return usuarioRepository.findUsuariosByUsernameContainingIgnoreCase(query)
  }

  fun findById(id: Int) = usuarioRepository.findByIdOrNull(id)
}
