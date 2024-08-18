DROP TABLE IF EXISTS mensaje;
DROP TABLE IF EXISTS chatxusuario;
DROP TABLE IF EXISTS chat;
DROP TABLE IF EXISTS usuario;

-- drop type IF EXISTS usuario_estado;
-- drop type IF EXISTS chat_tipo;
-- drop type IF EXISTS chatxusuario_rol;
-- drop type IF EXISTS mensaje_tipo;

-- Enum para el estado de usuario
-- create TYPE usuario_estado AS ENUM ('activo', 'inactivo', 'bloqueado');

-- Enum para el tipo de chat
-- CREATE TYPE chat_tipo AS ENUM ('individual', 'grupal');

-- Enum para el rol de usuario en el chat
-- CREATE TYPE chatxusuario_rol AS ENUM ('administrador', 'miembro');

-- Enum para el tipo de mensaje
-- CREATE TYPE mensaje_tipo AS ENUM ('texto', 'imagen', 'archivo');

-- Tabla usuario
CREATE TABLE usuario (
    id_usuario INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    hashed_password VARCHAR(256) NOT NULL,
    email VARCHAR(100),
    fecha_registro TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado usuario_estado DEFAULT 'activo'
);


-- Tabla chat
CREATE TABLE chat (
    id_chat INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descripcion VARCHAR(100),
    fecha_creacion TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo chat_tipo DEFAULT 'individual',
    imagen VARCHAR(256)
);

-- Tabla chatxusuario
CREATE TABLE chatxusuario (
    id_chat INT,
    id_usuario INT,
    rol chatxusuario_rol DEFAULT 'miembro',
    PRIMARY KEY (id_chat, id_usuario),
    FOREIGN KEY (id_chat) REFERENCES chat(id_chat) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla mensajes
CREATE TABLE mensaje (
    id_mensaje INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_chat INT,
    id_usuario INT,
    fecha_hora TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    contenido VARCHAR(256),
    tipo mensaje_tipo DEFAULT 'texto',
    FOREIGN KEY (id_chat) REFERENCES chat(id_chat) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Índice para fecha_hora en mensajes
CREATE INDEX idx_fecha_hora ON mensaje(fecha_hora DESC);
