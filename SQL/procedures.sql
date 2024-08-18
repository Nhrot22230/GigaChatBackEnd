DROP function IF EXISTS IniciarSesion;
DROP function IF EXISTS Registrarusuario;
DROP function IF EXISTS Actualizarusuario;
DROP function IF EXISTS Eliminarusuario;
DROP function IF EXISTS Obtenerusuario;
DROP function IF EXISTS ObtenerUsuarios;
DROP function IF EXISTS Crearchat;
DROP function IF EXISTS Actualizarchat;
DROP function IF EXISTS Eliminarchat;
DROP function IF EXISTS Obtenerchatsxusuarios; 
DROP function IF EXISTS Obtener_chats_usuario_mensaje;
DROP function IF EXISTS Agregarusuariochat;
DROP function IF EXISTS Eliminarusuariochat;
DROP function IF EXISTS Actualizarusuariochat;
DROP function IF EXISTS GuardarMensaje;

-- ==================================
-- Login function
-- ==================================

CREATE FUNCTION IniciarSesion(
    IN p_username VARCHAR(50),
    IN p_password VARCHAR(256)
)
RETURNS TABLE (
    id_usuario INT,
    username VARCHAR,
    email VARCHAR,
    fecha_registro TIMESTAMP WITH TIME zone,
    estado usuario_estado
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT u.id_usuario, u.username, u.email, u.fecha_registro, u.estado
    FROM usuario u
    WHERE u.username = p_username AND u.hashed_password = p_password;
END;
$$;

-- ==================================
-- usuarios function
-- ==================================

CREATE FUNCTION Registrarusuario(
    IN p_username VARCHAR(50),
    IN p_password VARCHAR(256),
    IN p_email VARCHAR(100),
    IN p_estado varchar(10)
)
RETURNS INT
LANGUAGE plpgsql AS $$
DECLARE
    v_id_usuario INT;
BEGIN
    INSERT INTO usuario (username, hashed_password, email, estado, fecha_registro)
    VALUES (p_username, p_password, p_email, p_estado::usuario_estado, NOW())
    RETURNING id_usuario INTO v_id_usuario;

    RETURN v_id_usuario;
END;
$$;

CREATE FUNCTION Actualizarusuario(
    IN p_id_usuario INT,
    IN p_username VARCHAR(50),
    IN p_password VARCHAR(256),
    IN p_email VARCHAR(100),
    IN p_estado varchar(10)
)
RETURNS VOID
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE usuario
    SET username = p_username, hashed_password = p_password, email = p_email, estado = p_estado::usuario_estado
    WHERE id_usuario = p_id_usuario;
END;
$$;


CREATE FUNCTION Eliminarusuario(
    IN p_id_usuario INT
)
RETURNS VOID
LANGUAGE SQL AS $$
    UPDATE usuario
    SET estado = 'inactivo'
    WHERE id_usuario = p_id_usuario;
$$;

CREATE FUNCTION Obtenerusuario(
    IN p_id_usuario INT
)
RETURNS TABLE (
    id_usuario INT, 
    username VARCHAR, 
    email VARCHAR,
    fecha_registro TIMESTAMP WITH TIME zone,
    estado usuario_estado
)
LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY
    SELECT u.id_usuario, u.username, u.email, u.fecha_registro, u.estado
    FROM usuario u
    WHERE u.id_usuario = p_id_usuario;
END;
$$;


CREATE FUNCTION ObtenerUsuarios(
    IN p_search VARCHAR(100)
)
RETURNS TABLE (
    id_usuario INT, 
    username VARCHAR, 
    email VARCHAR,
    fecha_registro TIMESTAMP WITH TIME zone,
    estado usuario_estado
)
LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY
    SELECT u.id_usuario, u.username, u.email, u.fecha_registro, u.estado
    FROM usuario u
    WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', p_search, '%'))
    ORDER BY u.id_usuario;
END;
$$;


-- ==================================
-- chats function
-- ==================================

CREATE FUNCTION Crearchat(
    IN p_titulo VARCHAR(100),
    IN p_descripcion VARCHAR(100),
    IN p_tipo varchar(10),
    IN p_imagen VARCHAR(256),
    IN p_fecha_creacion TIMESTAMP WITH TIME ZONE
)
RETURNS INT
LANGUAGE plpgsql AS $$
DECLARE
    v_id_chat INT;
BEGIN
    INSERT INTO chat (titulo, descripcion, tipo, imagen, fecha_creacion)
    VALUES (p_titulo, p_descripcion, p_tipo::chat_tipo, p_imagen, p_fecha_creacion)
    RETURNING id_chat INTO v_id_chat;

    RETURN v_id_chat;
END;
$$;


CREATE FUNCTION Actualizarchat(
    IN p_id_chat INT,
    IN p_titulo VARCHAR(100),
    IN p_descripcion VARCHAR(100),
    IN p_tipo varchar(10),
    IN p_imagen VARCHAR(256)
)
RETURNS VOID
LANGUAGE SQL AS $$
    UPDATE chat
    SET titulo = p_titulo, descripcion = p_descripcion, tipo = p_tipo::chat_tipo, imagen = p_imagen
    WHERE id_chat = p_id_chat;
$$;


CREATE FUNCTION Eliminarchat(
    IN p_id_chat INT
)
RETURNS VOID
LANGUAGE SQL AS $$
    DELETE FROM chat
    WHERE id_chat = p_id_chat;
$$;


-- ==================================
-- ChatXUsuario function
-- ==================================

CREATE FUNCTION Obtenerchatsxusuarios(
    IN p_id_usuario INT
)
RETURNS TABLE (
    id_chat INT, 
    titulo VARCHAR, 
    descripcion VARCHAR, 
    fecha_creacion TIMESTAMP WITH TIME ZONE, 
    tipo chat_tipo, 
    imagen VARCHAR,
    id_usuario INT, 
    username VARCHAR,
    email VARCHAR, 
    fecha_registro TIMESTAMP WITH TIME ZONE, 
    estado usuario_estado
) 
LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY
    SELECT c.id_chat, c.titulo, c.descripcion, c.fecha_creacion, c.tipo, c.imagen,
           u.id_usuario, u.username, u.email, u.fecha_registro, u.estado
    FROM chat c
    INNER JOIN chatxusuario cu ON cu.id_chat = c.id_chat
    INNER JOIN usuario u ON u.id_usuario = cu.id_usuario
    INNER JOIN chatxusuario cu2 ON cu2.id_chat = c.id_chat
    WHERE cu2.id_usuario = p_id_usuario;
END;
$$;


CREATE FUNCTION Obtener_chats_usuario_mensaje(
    IN p_id_usuario INT
)
RETURNS TABLE (
    c_id INT, 
    m_fecha_hora TIMESTAMP WITH TIME ZONE, 
    m_contenido VARCHAR, 
    m_tipo mensaje_tipo, 
    u_id INT, 
    u_name VARCHAR, 
    u_email VARCHAR, 
    u_fecha_registro TIMESTAMP WITH TIME ZONE, 
    u_estado usuario_estado
)
LANGUAGE plpgsql AS $$
BEGIN
    RETURN QUERY
    SELECT subquery.id_chat, 
           subquery.fecha_hora, 
           subquery.contenido, 
           subquery.tipo, 
           subquery.id_usuario, 
           subquery.username, 
           subquery.email, 
           subquery.fecha_registro, 
           subquery.estado
    FROM (
        SELECT m.id_chat, 
               m.fecha_hora, 
               m.contenido, 
               m.tipo, 
               m.id_usuario,
               u.username, 
               u.email, 
               u.fecha_registro, 
               u.estado,
               ROW_NUMBER() OVER (PARTITION BY m.id_chat ORDER BY m.fecha_hora DESC) AS row_num
        FROM mensaje m
        JOIN chatxusuario cu ON m.id_chat = cu.id_chat
        JOIN usuario u ON m.id_usuario = u.id_usuario
        WHERE cu.id_usuario = p_id_usuario
    ) subquery
    WHERE subquery.row_num <= 50
    ORDER BY subquery.id_chat, subquery.fecha_hora ASC;
END;
$$;



CREATE FUNCTION Agregarusuariochat(
    IN p_id_chat INT,
    IN p_id_usuario INT
)
RETURNS VOID
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO chatxusuario (id_chat, id_usuario, rol)
    VALUES (p_id_chat, p_id_usuario, 'miembro');
END;
$$;


CREATE FUNCTION Eliminarusuariochat(
    IN p_id_chat INT,
    IN p_id_usuario INT
)
RETURNS VOID
LANGUAGE plpgsql AS $$
BEGIN
    DELETE FROM chatxusuario
    WHERE id_chat = p_id_chat AND id_usuario = p_id_usuario;
END;
$$;


CREATE FUNCTION Actualizarusuariochat(
    IN p_id_chat INT,
    IN p_id_usuario INT,
    IN p_rol varchar(10)
)
RETURNS VOID
LANGUAGE plpgsql AS $$
BEGIN
    UPDATE chatxusuario
    SET rol = p_rol::chatxusuario_rol
    WHERE id_chat = p_id_chat AND id_usuario = p_id_usuario;
END;
$$;


CREATE FUNCTION GuardarMensaje(
    IN p_id_chat INT,
    IN p_id_usuario INT,
    IN p_contenido VARCHAR(256),
    IN p_tipo varchar(10)
)
RETURNS VOID
LANGUAGE plpgsql AS $$
BEGIN
    INSERT INTO mensaje (id_chat, id_usuario, contenido, tipo)
    VALUES (p_id_chat, p_id_usuario, p_contenido, p_tipo::mensaje_tipo);
END;
$$;





