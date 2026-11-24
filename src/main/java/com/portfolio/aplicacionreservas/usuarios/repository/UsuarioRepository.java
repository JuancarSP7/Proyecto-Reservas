package com.portfolio.aplicacionreservas.usuarios.repository;

import com.portfolio.aplicacionreservas.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para acceder a la tabla "usuarios".
 *
 * Gestiona la entidad Usuario, cuya clave primaria es de tipo Long.
 * Hereda de JpaRepository los métodos básicos: save, findById, findAll, deleteById...
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su email.
     * Consulta generada: SELECT * FROM usuarios WHERE email = ?
     * La usaremos en el login: el usuario escribe su email y buscamos sus datos.
     * Devuelve Optional porque puede que no exista ningún usuario con ese email.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Comprueba si ya existe un usuario con ese email.
     * Consulta generada: SELECT COUNT(*) > 0 FROM usuarios WHERE email = ?
     *
     * La usaremos en el registro para no permitir dos cuentas con el mismo email.
     * Devuelve true si existe y false si no.
     */
    boolean existsByEmail(String email);
}