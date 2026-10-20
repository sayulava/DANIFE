CREATE DATABASE IF NOT EXISTS danife;

USE danife;

CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,
    descripcion VARCHAR(255),
    CONSTRAINT chk_precio CHECK (precio >= 0),
    CONSTRAINT chk_cantidad CHECK (cantidad >= 0)
);

INSERT INTO productos
(nombre, categoria, precio, cantidad, descripcion)
VALUES
('Blusa elegante', 'Ropa', 65000.00, 10, 'Blusa para dama'),
('Bolso casual', 'Bolsos', 85000.00, 5, 'Bolso de uso diario'),
('Zapatos dama', 'Zapatos', 120000.00, 8, 'Zapatos elegantes');