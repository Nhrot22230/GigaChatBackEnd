package com.nofriends.gigachat.repository

import com.nofriends.gigachat.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Int> {

  @Query(
    value = "SELECT * FROM IniciarSesion(:p_username, :p_password)",
    nativeQuery = true
  )
  fun iniciarSesion(@Param("p_username") username: String, @Param("p_password") password: String): Usuario?

  @Query(
    value = "SELECT RegistrarUsuario(:p_username, :p_password, :p_email, :p_estado)",
    nativeQuery = true
  )
  fun registrarUsuario(
    @Param("p_username") username: String,
    @Param("p_password") password: String,
    @Param("p_email") email: String,
    @Param("p_estado") estado: String?
  ): Int

  @Query(
    value = "SELECT ActualizarUsuario(:p_id_usuario, :p_username, :p_password, :p_email, :p_estado)",
    nativeQuery = true
  )
  fun actualizarUsuario(
    @Param("p_id_usuario") idUsuario: Int,
    @Param("p_username") username: String,
    @Param("p_password") password: String,
    @Param("p_email") email: String,
    @Param("p_estado") estado: String
  )

  @Query(
    value = "SELECT EliminarUsuario(:p_id_usuario)",
    nativeQuery = true
  )
  fun eliminarUsuario(@Param("p_id_usuario") idUsuario: Int)

  fun findUsuariosByUsernameContainingIgnoreCase(
    @Param("username") username: String
  ): List<Usuario>
}
