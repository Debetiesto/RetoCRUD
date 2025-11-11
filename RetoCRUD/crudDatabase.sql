DROP DATABASE IF EXISTS RETOCRUD;

CREATE DATABASE RETOCRUD;

USE RETOCRUD;

CREATE TABLE PERFIL
(CODU INT PRIMARY KEY auto_increment,
EMAIL VARCHAR(100) NOT NULL,
USERNAME VARCHAR(50),
TELEFONO INT,
CONTRA VARCHAR(100) NOT NULL,
NOMBRE VARCHAR(50),
APELLIDOS VARCHAR(50)
);

CREATE TABLE USUARIO
(CODU INT PRIMARY KEY auto_increment,
GENERO ENUM ('HOMBRE', 'MUJER', 'OTRO') NOT NULL,
NUM_TARJETA INT NOT NULL,
FOREIGN KEY (CODU) REFERENCES PERFIL(CODU) ON DELETE CASCADE
);

CREATE TABLE ADMINISTRADOR
(CODU INT PRIMARY KEY auto_increment,
CUENTA_CORRIENTE VARCHAR(50) NOT NULL,
FOREIGN KEY (CODU) REFERENCES PERFIL(CODU)
);


-- 1️⃣ Insertamos perfiles base (con contraseña)
INSERT INTO PERFIL (EMAIL, USERNAME, TELEFONO, CONTRA, NOMBRE, APELLIDOS)
VALUES
('ana@gmail.com', 'anita_99', 612345678, 'passAna123', 'Ana', 'López García'),
('marco@hotmail.com', 'marcog', 698745632, 'claveMarco', 'Marco', 'Gómez Pérez'),
('lucas@empresa.com', 'lucas_admin', 666111222, 'adminLucas!', 'Lucas', 'Fernández Ruiz');

-- 2️⃣ Insertamos usuarios (enlazados con los perfiles 1 y 2)
INSERT INTO USUARIO (CODU, GENERO, NUM_TARJETA)
VALUES
(1, 'Mujer', 123456789),
(2, 'Hombre', 987654321);

-- 3️⃣ Insertamos un administrador (enlazado con el perfil 3)
INSERT INTO ADMINISTRADOR (CODU, CUENTA_CORRIENTE)
VALUES
(3, 'ES9121000418450200051332');

DELIMITER //

CREATE PROCEDURE InsertarUsuarioCompleto2(
    IN pEmail VARCHAR(100),
    IN pUsername VARCHAR(50),
    IN pTelefono INT,
    IN pContra VARCHAR(100),
    IN pNombre VARCHAR(50),
    IN pApellidos VARCHAR(50),
    IN pGenero ENUM('HOMBRE','MUJER','OTRO'),
    IN pNumTarjeta INT
)
BEGIN
    DECLARE nuevoID INT;

    -- 1️⃣ Insertar en PERFIL
    INSERT INTO PERFIL (EMAIL, USERNAME, TELEFONO, CONTRA, NOMBRE, APELLIDOS)
    VALUES (pEmail, pUsername, pTelefono, pContra, pNombre, pApellidos);

    -- Obtener el ID generado automáticamente
    SET nuevoID = LAST_INSERT_ID();

    -- 2️⃣ Insertar en USUARIO con el CODU generado
    INSERT INTO USUARIO (CODU, GENERO, NUM_TARJETA)
    VALUES (nuevoID, pGenero, pNumTarjeta);
END //

DELIMITER ;



