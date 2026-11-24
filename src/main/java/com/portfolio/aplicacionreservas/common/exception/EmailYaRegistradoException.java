package com.portfolio.aplicacionreservas.common.exception;

/**
 * Se lanza al intentar registrar un usuario con un email que ya existe.
 * Más adelante la convertiremos en una respuesta HTTP 409 (Conflict).
 */
public class EmailYaRegistradoException extends RuntimeException {

    public EmailYaRegistradoException(String email) {
        super("Ya existe un usuario registrado con el email: " + email);
    }
}