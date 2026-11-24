package com.portfolio.aplicacionreservas.usuarios.entity;

// Anotaciones de JPA (Jakarta Persistence): sirven para "mapear" la clase a una tabla
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Anotaciones de Lombok: generan código repetitivo automáticamente al compilar
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa un rol de la aplicación (ROLE_USER, ROLE_MONITOR, ROLE_ADMIN).
 * Cada objeto Rol corresponde a una fila de la tabla "roles".
 */

// @Entity → indica a JPA/Hibernate que esta clase es una ENTIDAD,
// es decir, que sus objetos se guardan y se leen de una tabla de la base de datos.
@Entity

// @Table → indica el nombre exacto de la tabla. Si no se pone, JPA usaría
// el nombre de la clase ("rol"), y nuestra tabla se llama "roles".
@Table(name = "roles")

// @Getter (Lombok) → genera automáticamente un getter para cada campo: getId(), getNombre()
@Getter

// @Setter (Lombok) → genera automáticamente un setter para cada campo: setId(), setNombre()
@Setter

// @NoArgsConstructor (Lombok) → genera un constructor vacío: new Rol()
// JPA lo NECESITA para poder crear los objetos cuando lee datos de la base de datos.
@NoArgsConstructor
public class Rol {

    // @Id → marca este campo como la CLAVE PRIMARIA de la tabla (columna "id").
    @Id

    // @GeneratedValue → el valor del id no lo ponemos nosotros, lo genera la base de datos.
    // IDENTITY → usa el autoincremento de la columna (el BIGSERIAL de nuestro script SQL).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column → configura la columna asociada a este campo:
    //   nullable = false → no puede estar vacía (NOT NULL)
    //   unique = true    → no puede haber dos roles con el mismo nombre (UNIQUE)
    //   length = 30      → longitud máxima del texto (VARCHAR(30))
    // Debe coincidir con lo que definimos en el script V1 de Flyway.
    @Column(nullable = false, unique = true, length = 30)
    private String nombre;
}