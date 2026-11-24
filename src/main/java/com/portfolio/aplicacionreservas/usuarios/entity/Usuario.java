package com.portfolio.aplicacionreservas.usuarios.entity;

// Anotaciones de JPA para mapear la clase a la tabla "usuarios"
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

// Anotaciones de Lombok
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Clases de Java que usamos en los campos
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa a un usuario registrado en la aplicación.
 * Cada objeto Usuario corresponde a una fila de la tabla "usuarios".
 * Un usuario puede tener varios roles (relación N:M con Rol).
 */

// @Entity → esta clase es una entidad que se guarda en la base de datos.
@Entity

// @Table → nombre exacto de la tabla en la base de datos.
@Table(name = "usuarios")

// @Getter / @Setter (Lombok) → generan getters y setters de todos los campos.
// OJO: en los campos boolean, Lombok genera "isActivo()" en lugar de "getActivo()".
@Getter
@Setter

// @NoArgsConstructor (Lombok) → constructor vacío, obligatorio para JPA.
@NoArgsConstructor

// NOTA: no usamos @Data de Lombok en entidades. @Data genera también equals(), hashCode()
// y toString() con TODOS los campos, y con relaciones (como roles) puede provocar
// bucles infinitos y errores difíciles de encontrar.
public class Usuario {

    // @Id → clave primaria de la tabla.
    @Id

    // @GeneratedValue(IDENTITY) → el id lo genera la base de datos (BIGSERIAL).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column → columna obligatoria (NOT NULL) de máximo 100 caracteres.
    @Column(nullable = false, length = 100)
    private String nombre;

    // @Column → columna obligatoria de máximo 150 caracteres.
    @Column(nullable = false, length = 150)
    private String apellidos;

    // @Column → obligatoria, máximo 150 caracteres y ÚNICA:
    // no puede haber dos usuarios con el mismo email (lo usarán para iniciar sesión).
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    // @Column → obligatoria. Aquí NO se guarda la contraseña tal cual,
    // sino cifrada con BCrypt (lo haremos en la fase de seguridad).
    // Se llama "password" porque es el nombre que usa Spring Security (getPassword()).
    @Column(nullable = false)
    private String password;

    // @Column → obligatoria. Indica si el usuario está activo.
    // "= true" → valor por defecto al crear un usuario nuevo en Java.
    @Column(nullable = false)
    private boolean activo = true;

    // @Column:
    //   name = "fecha_registro" → nombre de la columna en la tabla (en Java usamos camelCase,
    //                             en SQL usamos snake_case con guion bajo)
    //   updatable = false       → una vez guardada, la fecha de registro no se puede modificar
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    // ===================== RELACIÓN N:M CON ROLES =====================

    // @ManyToMany → relación "muchos a muchos": un usuario tiene varios roles
    // y un mismo rol lo tienen muchos usuarios.
    //   fetch = FetchType.EAGER → al cargar un usuario, se cargan también sus roles
    //                             en ese momento (los necesitaremos siempre en la seguridad).
    @ManyToMany(fetch = FetchType.EAGER)

    // @JoinTable → describe la TABLA INTERMEDIA que une usuarios y roles:
    //   name               → nombre de la tabla intermedia: "usuario_roles"
    //   joinColumns        → columna que apunta a ESTA entidad (Usuario): "usuario_id"
    //   inverseJoinColumns → columna que apunta a la OTRA entidad (Rol): "rol_id"
    // En Java solo vemos un Set<Rol>; JPA se encarga de leer y escribir en usuario_roles.
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    // Usamos Set (y no List) porque un usuario no puede tener el mismo rol repetido.
    // Se inicializa vacío para evitar NullPointerException al añadir roles.
    private Set<Rol> roles = new HashSet<>();

    // ===================== MÉTODOS DEL CICLO DE VIDA =====================

    // @PrePersist → este método se ejecuta AUTOMÁTICAMENTE justo antes de guardar
    // el usuario por primera vez (en el INSERT). Lo usamos para poner la fecha de registro.
    @PrePersist
    void alCrear() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
    }
}