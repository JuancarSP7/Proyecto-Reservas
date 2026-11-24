package com.portfolio.aplicacionreservas.usuarios.dto;

import com.portfolio.aplicacionreservas.usuarios.entity.Rol;
import com.portfolio.aplicacionreservas.usuarios.entity.Usuario;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Datos de un usuario que devolvemos al cliente.
 *
 * IMPORTANTE: no incluye la contraseña. Por eso usamos un DTO en lugar de
 * devolver la entidad Usuario directamente.
 */
public record UsuarioResponse(
        Long id,
        String nombre,
        String apellidos,
        String email,
        boolean activo,
        LocalDateTime fechaRegistro,
        Set<String> roles      // solo los nombres: ["ROLE_USER"]
) {

    /**
     * Convierte una entidad Usuario en un UsuarioResponse.
     * Es "static" → se llama sin crear un objeto: UsuarioResponse.desdeEntidad(usuario)
     */
    public static UsuarioResponse desdeEntidad(Usuario usuario) {
        // Pasamos de Set<Rol> a Set<String> quedándonos solo con el nombre de cada rol:
        //   stream()            → recorre los roles uno a uno
        //   map(Rol::getNombre) → de cada Rol se queda con su nombre
        //   collect(toSet())    → lo junta todo en un Set<String>
        Set<String> nombresRoles = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet());

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.isActivo(),          // en boolean, Lombok genera isActivo()
                usuario.getFechaRegistro(),
                nombresRoles
        );
    }
}