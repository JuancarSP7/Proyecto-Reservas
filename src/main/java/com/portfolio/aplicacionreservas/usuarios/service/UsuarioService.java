package com.portfolio.aplicacionreservas.usuarios.service;

import com.portfolio.aplicacionreservas.common.exception.EmailYaRegistradoException;
import com.portfolio.aplicacionreservas.common.exception.RecursoNoEncontradoException;
import com.portfolio.aplicacionreservas.usuarios.dto.RegistroUsuarioRequest;
import com.portfolio.aplicacionreservas.usuarios.dto.UsuarioResponse;
import com.portfolio.aplicacionreservas.usuarios.entity.Rol;
import com.portfolio.aplicacionreservas.usuarios.entity.Usuario;
import com.portfolio.aplicacionreservas.usuarios.repository.RolRepository;
import com.portfolio.aplicacionreservas.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lógica de negocio de los usuarios.
 *
 * El service es la capa intermedia:
 *   controller (recibe la petición) → SERVICE (aplica las reglas) → repository (base de datos)
 * Aquí van las reglas: "no puede haber dos usuarios con el mismo email",
 * "la contraseña se guarda cifrada", "todo usuario nuevo recibe ROLE_USER"...
 */

// @Service → marca la clase como un componente de lógica de negocio.
// Spring crea un único objeto de esta clase y lo inyecta donde se necesite (el controller).
@Service

// @RequiredArgsConstructor (Lombok) → genera un constructor con todos los campos "final".
// Spring usa ese constructor para INYECTAR las dependencias automáticamente
// (es la forma recomendada, en lugar de poner @Autowired en cada campo).
@RequiredArgsConstructor
public class UsuarioService {

    private static final String ROL_POR_DEFECTO = "ROLE_USER";

    // Dependencias que Spring nos pasa por el constructor
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registra un usuario nuevo con el rol ROLE_USER.
     *
     * @param datos los datos que envía el cliente
     * @return el usuario creado (sin contraseña)
     * @throws EmailYaRegistradoException si el email ya está en uso
     */
    // @Transactional → todo el método se ejecuta como UNA sola operación en la base de datos.
    // Si algo falla a mitad, se deshace todo (no quedan datos guardados a medias).
    @Transactional
    public UsuarioResponse registrarUsuario(RegistroUsuarioRequest datos) {

        // Guardamos el email en minúsculas y sin espacios: "Ana@Mail.com " → "ana@mail.com"
        String email = datos.email().trim().toLowerCase();

        // Regla 1: el email no puede estar repetido
        if (usuarioRepository.existsByEmail(email)) {
            throw new EmailYaRegistradoException(email);
        }

        // Regla 2: todo usuario nuevo recibe el rol ROLE_USER.
        // Si no existiera en la base de datos sería un fallo de configuración,
        // así que lanzamos IllegalStateException ("estado no válido").
        Rol rolUsuario = rolRepository.findByNombre(ROL_POR_DEFECTO)
                .orElseThrow(() -> new IllegalStateException(
                        "No existe el rol " + ROL_POR_DEFECTO + " en la base de datos"));

        // Creamos la entidad con los datos recibidos
        Usuario usuario = new Usuario();
        usuario.setNombre(datos.nombre().trim());
        usuario.setApellidos(datos.apellidos().trim());
        usuario.setEmail(email);

        // Regla 3: la contraseña NUNCA se guarda tal cual, siempre cifrada con BCrypt
        usuario.setPassword(passwordEncoder.encode(datos.password()));

        // Añadimos el rol. Al guardar, JPA insertará automáticamente
        // una fila en la tabla intermedia usuario_roles.
        usuario.getRoles().add(rolUsuario);

        // save() hace el INSERT y devuelve el usuario ya guardado (con su id generado)
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Devolvemos el DTO de respuesta, nunca la entidad
        return UsuarioResponse.desdeEntidad(usuarioGuardado);
    }

    /**
     * Busca un usuario por su id.
     *
     * @throws RecursoNoEncontradoException si no existe
     */
    // readOnly = true → avisa de que este método solo LEE datos.
    // Así la base de datos puede optimizar la consulta.
    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe ningún usuario con id " + id));

        return UsuarioResponse.desdeEntidad(usuario);
    }
}