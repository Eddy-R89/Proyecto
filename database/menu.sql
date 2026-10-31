-- Base de datos de ejemplo para la tienda Parada
-- Ejecutar este archivo en MySQL Workbench.
-- No elimina tablas existentes; crea las tablas solo si aún no existen.

CREATE DATABASE IF NOT EXISTS menu
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE menu;

-- PRODUCTO
CREATE TABLE IF NOT EXISTS producto (
    Codp INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nproducto VARCHAR(100) NOT NULL,
    detalle VARCHAR(150),
    existencias DECIMAL(10,2) NOT NULL DEFAULT 0,
    PRECIO DECIMAL(10,2) NOT NULL
) ENGINE=InnoDB;

-- CLIENTE
CREATE TABLE IF NOT EXISTS cliente (
    nit VARCHAR(20) PRIMARY KEY,
    nombrec VARCHAR(200) NOT NULL,
    direccion VARCHAR(200)
) ENGINE=InnoDB;

-- ORDEN
CREATE TABLE IF NOT EXISTS orden (
    id_orden INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    nit_cliente VARCHAR(20) NOT NULL,
    CONSTRAINT fk_orden_cliente
        FOREIGN KEY (nit_cliente) REFERENCES cliente(nit)
) ENGINE=InnoDB;

-- PAGOS
CREATE TABLE IF NOT EXISTS pagos (
    id_pago INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    tipo_pago ENUM('efectivo', 'tarjeta') NOT NULL,
    id_orden INT UNSIGNED NOT NULL,
    CONSTRAINT uq_pagos_orden UNIQUE (id_orden),
    CONSTRAINT fk_pagos_orden
        FOREIGN KEY (id_orden) REFERENCES orden(id_orden)
) ENGINE=InnoDB;

-- DETALLE DE LA ORDEN
CREATE TABLE IF NOT EXISTS detalle_orden (
    id_detalle INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_orden INT UNSIGNED NOT NULL,
    id_producto INT UNSIGNED NOT NULL,
    cantidad INT UNSIGNED NOT NULL,
    CONSTRAINT fk_detalle_orden
        FOREIGN KEY (id_orden) REFERENCES orden(id_orden),
    CONSTRAINT fk_detalle_producto
        FOREIGN KEY (id_producto) REFERENCES producto(Codp),
    CONSTRAINT uq_detalle_producto UNIQUE (id_orden, id_producto)
) ENGINE=InnoDB;

-- ENVÍO
CREATE TABLE IF NOT EXISTS envio (
    id_envio INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    metodo_envio VARCHAR(200) NOT NULL,
    id_orden_envio INT UNSIGNED NOT NULL,
    CONSTRAINT uq_envio_orden UNIQUE (id_orden_envio),
    CONSTRAINT fk_envio_orden
        FOREIGN KEY (id_orden_envio) REFERENCES orden(id_orden)
) ENGINE=InnoDB;

-- DATOS DE EJEMPLO. Los ID explícitos permiten volver a ejecutar las inserciones.
INSERT INTO producto (Codp, nproducto, detalle, existencias, PRECIO)
VALUES
    (1, 'Comida', 'Refrigerio', 200, 25.00),
    (2, 'Bebida', 'Refrigerio', 89, 19.00)
ON DUPLICATE KEY UPDATE
    nproducto = VALUES(nproducto),
    detalle = VALUES(detalle),
    existencias = VALUES(existencias),
    PRECIO = VALUES(PRECIO);

INSERT INTO cliente (nit, nombrec, direccion)
VALUES
    ('2018170', 'Henderick', '12 Broadway Blvd 18625'),
    ('1456798', 'Hades', '25632 Avenue')
ON DUPLICATE KEY UPDATE
    nombrec = VALUES(nombrec),
    direccion = VALUES(direccion);

INSERT INTO orden (id_orden, fecha, hora, nit_cliente)
VALUES
    (1, '2026-06-07', '14:30:00', '2018170'),
    (2, '2026-10-07', '21:30:00', '1456798')
ON DUPLICATE KEY UPDATE
    fecha = VALUES(fecha),
    hora = VALUES(hora),
    nit_cliente = VALUES(nit_cliente);

INSERT INTO pagos (id_pago, tipo_pago, id_orden)
VALUES
    (1, 'efectivo', 1),
    (2, 'efectivo', 2)
ON DUPLICATE KEY UPDATE
    tipo_pago = VALUES(tipo_pago),
    id_orden = VALUES(id_orden);

INSERT INTO detalle_orden (id_detalle, id_orden, id_producto, cantidad)
VALUES (1, 1, 2, 14)
ON DUPLICATE KEY UPDATE
    id_orden = VALUES(id_orden),
    id_producto = VALUES(id_producto),
    cantidad = VALUES(cantidad);

INSERT INTO envio (id_envio, metodo_envio, id_orden_envio)
VALUES
    (1, 'A DOMICILIO', 1),
    (2, 'EN TIENDA', 2)
ON DUPLICATE KEY UPDATE
    metodo_envio = VALUES(metodo_envio),
    id_orden_envio = VALUES(id_orden_envio);

-- CONSULTAS DE EJEMPLO
SELECT * FROM producto;
SELECT * FROM cliente;
SELECT * FROM orden;
SELECT * FROM pagos;
SELECT * FROM detalle_orden;
SELECT * FROM envio;

-- Total a pagar de la orden número 1
SELECT
    d.id_orden,
    SUM(d.cantidad * p.PRECIO) AS Total_Pagar
FROM detalle_orden AS d
JOIN producto AS p ON d.id_producto = p.Codp
WHERE d.id_orden = 1
GROUP BY d.id_orden;

-- Instrucción según el tipo de pago
SELECT
    p.id_orden,
    p.tipo_pago,
    CASE
        WHEN p.tipo_pago = 'efectivo' THEN 'PAGAR EN MOSTRADOR - Imprimir ticket'
        WHEN p.tipo_pago = 'tarjeta' THEN 'ORDEN PAGADA - Imprimir orden'
    END AS Instruccion
FROM pagos AS p;