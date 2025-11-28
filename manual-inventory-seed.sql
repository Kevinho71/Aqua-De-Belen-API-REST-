-- Script manual para poblar datos de inventario (EOQ, Reorden)
-- Ejecuta este script en tu herramienta de base de datos (pgAdmin, DBeaver, o consola de Supabase)

-- Producto 1
UPDATE producto 
SET costo_pedido = 25.0, 
    costo_almacenamiento = 2.0, 
    tiempo_entrega = 7, 
    stock_seguridad = 10 
WHERE id = 1;

-- Producto 2
UPDATE producto 
SET costo_pedido = 25.0, 
    costo_almacenamiento = 2.0, 
    tiempo_entrega = 7, 
    stock_seguridad = 10 
WHERE id = 2;

-- Producto 3
UPDATE producto 
SET costo_pedido = 15.0, 
    costo_almacenamiento = 5.0, 
    tiempo_entrega = 3, 
    stock_seguridad = 5 
WHERE id = 3;

-- Producto 4
UPDATE producto 
SET costo_pedido = 10.0, 
    costo_almacenamiento = 1.0, 
    tiempo_entrega = 2, 
    stock_seguridad = 20 
WHERE id = 4;

-- Valores por defecto para cualquier otro producto que no tenga datos (para evitar errores en cálculos)
UPDATE producto 
SET costo_pedido = 20.0, 
    costo_almacenamiento = 2.0, 
    tiempo_entrega = 5, 
    stock_seguridad = 10 
WHERE costo_pedido IS NULL;
