-- ============================================================
-- EquipaYa: script de la base de datos (parte a)
-- Probado en XAMPP (MariaDB 10.4). Se puede ejecutar varias veces.
-- ============================================================

DROP DATABASE IF EXISTS equipaya;
CREATE DATABASE equipaya CHARACTER SET utf8mb4;
USE equipaya;

-- ------------------------------------------------------------
-- CLIENTE
-- PK = documento (DNI de 8 digitos o RUC de 11). Es texto porque un
-- DNI puede empezar con cero. Al ser PK tiene indice: la consulta por
-- documento (req. 5) no recorre toda la tabla.
-- ------------------------------------------------------------
CREATE TABLE cliente (
    documento VARCHAR(11)  NOT NULL,
    nombre    VARCHAR(100) NOT NULL,
    telefono  VARCHAR(15),
    PRIMARY KEY (documento),
    CHECK (CHAR_LENGTH(documento) IN (8, 11))
);

-- ------------------------------------------------------------
-- EQUIPO
-- PK = codigo (texto, el que usa el mostrador).
-- tipo = texto que identifica la clase Java a crear (CAMARA,
-- ILUMINACION, AUDIO). No lleva CHECK con la lista de tipos a proposito:
-- agregar DRON el proximo ano no obliga a modificar la tabla (req. 6).
-- disponible = TRUE/FALSE (req. 2).
-- ------------------------------------------------------------
CREATE TABLE equipo (
    codigo        VARCHAR(10)   NOT NULL,
    descripcion   VARCHAR(100)  NOT NULL,
    tarifa_diaria DECIMAL(10,2) NOT NULL,
    disponible    BOOLEAN       NOT NULL DEFAULT TRUE,
    tipo          VARCHAR(20)   NOT NULL,
    PRIMARY KEY (codigo),
    CHECK (tarifa_diaria > 0)
);

-- ------------------------------------------------------------
-- ALQUILER
-- PK = id_alquiler (autoincremental, no hay un dato natural unico:
-- el mismo cliente puede alquilar el mismo equipo muchas veces).
-- Las FK tienen el mismo tipo y tamano que las PK a las que apuntan.
-- monto = lo cobrado, calculado en Java con la regla de cada tipo.
-- devuelto = FALSE mientras el equipo siga fuera (req. 4).
-- ------------------------------------------------------------
CREATE TABLE alquiler (
    id_alquiler       INT           NOT NULL AUTO_INCREMENT,
    documento_cliente VARCHAR(11)   NOT NULL,
    codigo_equipo     VARCHAR(10)   NOT NULL,
    fecha_inicio      DATE          NOT NULL DEFAULT (CURRENT_DATE),
    dias              INT           NOT NULL,
    monto             DECIMAL(10,2) NOT NULL,
    devuelto          BOOLEAN       NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id_alquiler),
    FOREIGN KEY (documento_cliente) REFERENCES cliente (documento),
    FOREIGN KEY (codigo_equipo)     REFERENCES equipo (codigo),
    CHECK (dias >= 1),
    CHECK (monto >= 0)
);

-- ------------------------------------------------------------
-- DATOS DE PRUEBA
-- ------------------------------------------------------------
INSERT INTO cliente (documento, nombre, telefono) VALUES
('04567890',    'Maria Quispe',             '987654321'),
('45678912',    'Carlos Rojas',             '976543210'),
('71234567',    'Ana Torres',               '965432109'),
('20512345678', 'Productora Luz Andina SAC', '014567890'),
('10765432198', 'Diana Salazar EIRL',       '954321098');

INSERT INTO equipo (codigo, descripcion, tarifa_diaria, disponible, tipo) VALUES
('CAM-001', 'Camara Sony FX3',          350.00, FALSE, 'CAMARA'),
('CAM-002', 'Camara Canon C70',         400.00, TRUE,  'CAMARA'),
('ILU-001', 'Panel LED Aputure 600d',   120.00, TRUE,  'ILUMINACION'),
('ILU-002', 'Kit de 3 softbox',          80.00, FALSE, 'ILUMINACION'),
('AUD-001', 'Microfono boom Rode NTG5',  40.00, TRUE,  'AUDIO'),
('AUD-002', 'Mezcladora Zoom F8',        90.00, TRUE,  'AUDIO');

-- Montos: camara = tarifa x dias x 1.15 | iluminacion = tarifa x dias
-- (x 0.90 si dias > 5) | audio = tarifa x max(dias, 2)
INSERT INTO alquiler (documento_cliente, codigo_equipo, fecha_inicio, dias, monto, devuelto) VALUES
('45678912',    'CAM-001', '2026-10-01', 2,  805.00, TRUE),
('45678912',    'CAM-002', '2026-10-10', 2,  920.00, TRUE),
('71234567',    'ILU-001', '2026-10-15', 7,  756.00, TRUE),
('04567890',    'AUD-001', '2026-10-20', 1,   80.00, TRUE),
('20512345678', 'AUD-002', '2026-10-25', 5,  450.00, TRUE),
('04567890',    'CAM-001', '2026-11-05', 3, 1207.50, FALSE),
('20512345678', 'ILU-002', '2026-11-08', 4,  320.00, FALSE);
