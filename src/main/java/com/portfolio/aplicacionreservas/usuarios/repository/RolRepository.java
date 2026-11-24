package com.portfolio.aplicacionreservas.usuarios.repository;

import com.portfolio.aplicacionreservas.usuarios.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para acceder a la tabla "roles".
 *
 * Es una INTERFAZ: no escribimos la implementación.
 * Spring Data JPA crea automáticamente una clase que la implementa al arrancar la app.
 *
 * Al extender JpaRepository<Rol, Long> ya tenemos, sin escribir nada:
 *   save(rol)      → guardar o actualizar
 *   findById(id)   → buscar por id
 *   findAll()      → obtener todos
 *   deleteById(id) → borrar por id
 *   count()        → contar filas
 *
 * Los dos tipos entre < > significan:
 *   Rol  → la entidad que gestiona este repositorio
 *   Long → el tipo de su clave primaria (el campo id)
 */
// No hace falta poner @Repository: Spring detecta automáticamente
// las interfaces que extienden JpaRepository.
public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Busca un rol por su nombre, por ejemplo "ROLE_USER".
     *
     * Spring genera la consulta SQL a partir del NOMBRE del método:
     *   findBy + Nombre → SELECT * FROM roles WHERE nombre = ?
     * ("findBy" va en inglés porque es una palabra clave de Spring;
     *  "Nombre" debe coincidir con el nombre del campo de la entidad).
     *
     * Devuelve Optional porque puede que el rol no exista:
     * así nos obliga a comprobarlo y evitamos un NullPointerException.
     */
    Optional<Rol> findByNombre(String nombre);
}