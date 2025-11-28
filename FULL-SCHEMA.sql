-- =============================================================================
-- ESQUEMA COMPLETO DE BASE DE DATOS - AQUA DE BELEN
-- Generado basado en los archivos de migración Liquibase y Entidades JPA
-- =============================================================================

-- 1. TABLAS INDEPENDIENTES (CATÁLOGOS)

CREATE TABLE ubicacion (
    id SERIAL PRIMARY KEY,
    ciudad VARCHAR(255),
    descripcion VARCHAR(255),
    direccion VARCHAR(255),
    latitud DOUBLE PRECISION,
    longitud DOUBLE PRECISION
);

CREATE TABLE nivel_fidelidad (
    id SERIAL PRIMARY KEY,
    nivel VARCHAR(255)
);

CREATE TABLE tipo_producto (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255)
);

CREATE TABLE rol (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255)
);

-- 2. TABLAS PRINCIPALES

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    password VARCHAR(255),
    username VARCHAR(255),
    rol_id INTEGER REFERENCES rol(id)
);

CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    apellido VARCHAR(255),
    direccion VARCHAR(255),
    nit_ci VARCHAR(255),
    telefono VARCHAR(255),
    nivel_fidelidad_id INTEGER REFERENCES nivel_fidelidad(id),
    ubicacion_id INTEGER REFERENCES ubicacion(id)
);

CREATE TABLE proveedor (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    contacto VARCHAR(255),
    correo VARCHAR(255),
    nit VARCHAR(20),
    telefono VARCHAR(20),
    ubicacion_id INTEGER REFERENCES ubicacion(id)
);

CREATE TABLE producto (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    descripcion VARCHAR(255),
    descontinuado BOOLEAN DEFAULT FALSE,
    clasificacion_abc VARCHAR(255),
    costo_pedido DOUBLE PRECISION,
    costo_almacenamiento DOUBLE PRECISION,
    tiempo_entrega INTEGER,
    stock_seguridad INTEGER,
    eoq INTEGER,
    punto_reorden INTEGER,
    tipo_producto_id INTEGER REFERENCES tipo_producto(id)
);

CREATE TABLE precios_historico (
    id SERIAL PRIMARY KEY,
    fecha TIMESTAMP WITHOUT TIME ZONE,
    precio_venta DOUBLE PRECISION,
    producto_id INTEGER REFERENCES producto(id)
);

-- 3. MÓDULO DE COMPRAS E INVENTARIO

CREATE TABLE compra (
    id SERIAL PRIMARY KEY,
    fecha TIMESTAMP WITHOUT TIME ZONE,
    costo_bruto DOUBLE PRECISION NOT NULL,
    costo_neto DOUBLE PRECISION NOT NULL,
    descuento_total DOUBLE PRECISION,
    proveedor_id INTEGER REFERENCES proveedor(id)
    -- Nota: lote_id se maneja a veces como relación 1 a 1 inversa o directa dependiendo del diseño exacto
);

CREATE TABLE lote (
    id SERIAL PRIMARY KEY,
    numero_lote VARCHAR(255),
    fecha_vencimiento TIMESTAMP WITHOUT TIME ZONE,
    observacion VARCHAR(255),
    compra_id INTEGER REFERENCES compra(id)
);

CREATE TABLE sublote (
    id SERIAL PRIMARY KEY,
    cantidad_inicial DOUBLE PRECISION,
    cantidad_actual DOUBLE PRECISION,
    codigo_sublote VARCHAR(100),
    fecha_produccion DATE,
    estado VARCHAR(20) DEFAULT 'DISPONIBLE',
    lote_id INTEGER REFERENCES lote(id),
    producto_id INTEGER REFERENCES producto(id),
    ubicacion_id INTEGER REFERENCES ubicacion(id)
);

CREATE TABLE detalle_compra (
    id SERIAL PRIMARY KEY,
    cantidad DOUBLE PRECISION,
    precio_unitario DOUBLE PRECISION, -- Costo unitario
    subtotal DOUBLE PRECISION,
    descuento DOUBLE PRECISION DEFAULT 0.0,
    fecha_vencimiento TIMESTAMP WITHOUT TIME ZONE,
    compra_id INTEGER REFERENCES compra(id),
    producto_id INTEGER REFERENCES producto(id),
    sublote_id INTEGER REFERENCES sublote(id)
);

-- 4. MÓDULO DE VENTAS

CREATE TABLE venta (
    id SERIAL PRIMARY KEY,
    fecha TIMESTAMP WITHOUT TIME ZONE,
    total DOUBLE PRECISION,
    cliente_id INTEGER REFERENCES cliente(id),
    usuario_id INTEGER REFERENCES usuario(id)
);

CREATE TABLE detalle_venta (
    id SERIAL PRIMARY KEY,
    cantidad DOUBLE PRECISION,
    precio_unitario DOUBLE PRECISION,
    subtotal DOUBLE PRECISION,
    producto_id INTEGER REFERENCES producto(id),
    venta_id INTEGER REFERENCES venta(id)
);

CREATE TABLE factura (
    numero SERIAL PRIMARY KEY,
    fecha_de_emision TIMESTAMP WITHOUT TIME ZONE,
    nit VARCHAR(255),
    razon_social VARCHAR(255),
    venta_id INTEGER NOT NULL UNIQUE REFERENCES venta(id)
);

-- 5. MÓDULO DE RESERVAS

CREATE TABLE reserva (
    id SERIAL PRIMARY KEY,
    fecha_reserva TIMESTAMP WITHOUT TIME ZONE,
    fecha_limite TIMESTAMP WITHOUT TIME ZONE,
    estado VARCHAR(255), -- PENDIENTE, CONFIRMADA, CANCELADA
    cliente_id INTEGER REFERENCES cliente(id)
);

CREATE TABLE detalle_reserva (
    id SERIAL PRIMARY KEY,
    cantidad INTEGER,
    producto_id INTEGER REFERENCES producto(id),
    reserva_id INTEGER REFERENCES reserva(id)
);

-- 6. TRAZABILIDAD

CREATE TABLE movimiento (
    id SERIAL PRIMARY KEY,
    fecha TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    cantidad DOUBLE PRECISION NOT NULL,
    tipo_movimiento VARCHAR(255), -- COMPRA, VENTA, AJUSTE
    costo_unitario DOUBLE PRECISION,
    precio_unitario DOUBLE PRECISION,
    costo_total DOUBLE PRECISION,
    precio_total DOUBLE PRECISION,
    referencia_tipo VARCHAR(50),
    referencia_id INTEGER,
    detalle_compra_id INTEGER REFERENCES detalle_compra(id),
    detalle_venta_id INTEGER REFERENCES detalle_venta(id)
);
