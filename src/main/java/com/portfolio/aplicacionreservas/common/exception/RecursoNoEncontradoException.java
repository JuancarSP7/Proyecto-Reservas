package com.portfolio.aplicacionreservas.common.exception;

/**
 * Se lanza cuando se busca algo que no existe (un usuario, una sesión...).
 * Más adelante la convertiremos en una respuesta HTTP 404 (Not Found).
 *
 * Extiende RuntimeException → es una excepción "no comprobada":
 * no obliga a poner try/catch ni "throws" en cada método.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        // super(...) → pasa el mensaje a la clase padre para que getMessage() lo devuelva
        super(mensaje);
    }
}