-- Roles de la aplicación
CREATE TABLE roles (
    id     BIGSERIAL   PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE
);

-- Usuarios registrados
CREATE TABLE usuarios (
    id             BIGSERIAL    PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    apellidos      VARCHAR(150) NOT NULL,
    email          VARCHAR(150) NOT NULL UNIQUE,
    password       VARCHAR(255) NOT NULL,
    activo         BOOLEAN      NOT NULL DEFAULT TRUE,
    fecha_registro TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabla intermedia N:M: un usuario puede tener varios roles y un rol muchos usuarios
CREATE TABLE usuario_roles (
    usuario_id BIGINT NOT NULL REFERENCES usuarios (id) ON DELETE CASCADE,
    rol_id     BIGINT NOT NULL REFERENCES roles (id),
    PRIMARY KEY (usuario_id, rol_id)
);

-- Roles iniciales
INSERT INTO roles (nombre) VALUES ('ROLE_USER'), ('ROLE_MONITOR'), ('ROLE_ADMIN');