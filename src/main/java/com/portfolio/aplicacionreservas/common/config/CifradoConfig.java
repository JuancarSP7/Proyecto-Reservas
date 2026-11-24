package com.portfolio.aplicacionreservas.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración del cifrado de contraseñas.
 */

// @Configuration → indica a Spring que esta clase contiene configuración
// y que debe leerla al arrancar la aplicación.
@Configuration
public class CifradoConfig {

    /**
     * Crea el objeto que cifra las contraseñas con el algoritmo BCrypt.
     *
     * BCrypt convierte "miClave123" en algo como "$2a$10$Xk9...".
     * Es un cifrado de una sola dirección: no se puede descifrar.
     * Para comprobar una contraseña en el login, se cifra lo que escribe
     * el usuario y se compara con lo guardado.
     */
    // @Bean → el objeto que devuelve este método lo crea y lo guarda Spring.
    // Así, cualquier clase que necesite un PasswordEncoder lo recibe automáticamente.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}