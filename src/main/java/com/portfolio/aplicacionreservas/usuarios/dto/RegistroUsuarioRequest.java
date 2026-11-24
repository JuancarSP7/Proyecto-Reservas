package com.portfolio.aplicacionreservas.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Datos que envía el cliente (Postman o Angular) para registrarse.
 * Llegan en el cuerpo de la petición en formato JSON:
 * { "nombre": "Ana", "apellidos": "López", "email": "ana@mail.com", "password": "secreta123" }
 *
 * Las anotaciones de validación se comprueban en el controller (con @Valid).
 * Si algún dato no cumple, la petición se rechaza antes de llegar al service.
 */
public record RegistroUsuarioRequest(

        // @NotBlank → no puede ser null, ni vacío "", ni solo espacios "   "
        // @Size(max) → longitud máxima, igual que en la tabla
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(max = 150, message = "Los apellidos no pueden superar los 150 caracteres")
        String apellidos,

        // @Email → comprueba que tiene formato de email (algo@algo)
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        @Size(max = 150, message = "El email no puede superar los 150 caracteres")
        String email,

        // @Size(min, max) → la contraseña debe tener entre 8 y 72 caracteres
        // (72 es el máximo que admite BCrypt)
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, max = 72, message = "La contraseña debe tener entre 8 y 72 caracteres")
        String password
) {
}