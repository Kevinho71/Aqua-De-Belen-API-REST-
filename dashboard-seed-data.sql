-- ================================================================================
-- SCRIPT DE DATOS PARA DASHBOARD - AQUA DE BELÉN
-- ================================================================================
-- Este script genera datos realistas para crear dashboards completos
-- Incluye: Proveedores, Compras, Lotes, Sublotes, Ventas, Precios, Movimientos
-- Fecha de generación: 18 Noviembre 2025
-- ================================================================================

-- ========================================
-- 1. PROVEEDORES (5 registros)
-- ========================================

INSERT INTO proveedor (id, nombre, correo, telefono, nit, ubicacion_id) VALUES
(1, 'Distribuidora La Paz', 'ventas@distrilpz.com', '2-2123456', '1020304050', 3),
(2, 'Importadora Santa Cruz', 'info@impscz.com', '3-3456789', '2030405060', 2),
(3, 'Cosméticos del Valle', 'contacto@cosmeticos.bo', '4-4567890', '3040506070', 1),
(4, 'Perfumerías Unidas', 'compras@perfumerias.bo', '2-2234567', '4050607080', 3),
(5, 'BeautyWorld Importaciones', 'ventas@beautyworld.bo', '3-3567891', '5060708090', 2);

-- ========================================
-- 2. LOTES (8 registros - diferentes fechas)
-- ========================================

INSERT INTO lote (id, fecha_ingreso) VALUES
(1, '2025-09-15 10:30:00'),
(2, '2025-09-28 14:15:00'),
(3, '2025-10-05 09:45:00'),
(4, '2025-10-12 11:20:00'),
(5, '2025-10-20 15:30:00'),
(6, '2025-10-28 10:00:00'),
(7, '2025-11-05 13:45:00'),
(8, '2025-11-12 16:30:00');

-- ========================================
-- 3. COMPRAS (8 registros vinculados a lotes)
-- ========================================

-- Compra 1: Lote 1 - Septiembre
INSERT INTO compra (id, costo_bruto, costo_neto, descuento_total, fecha, lote_id, proveedor_id) VALUES
(1, 8500.00, 8075.00, 425.00, '2025-09-15 10:30:00', 1, 1);

-- Compra 2: Lote 2 - Septiembre
INSERT INTO compra (id, costo_bruto, costo_neto, descuento_total, fecha, lote_id, proveedor_id) VALUES
(2, 12000.00, 11400.00, 600.00, '2025-09-28 14:15:00', 2, 2);

-- Compra 3: Lote 3 - Octubre
INSERT INTO compra (id, costo_bruto, costo_neto, descuento_total, fecha, lote_id, proveedor_id) VALUES
(3, 6800.00, 6596.00, 204.00, '2025-10-05 09:45:00', 3, 3);

-- Compra 4: Lote 4 - Octubre
INSERT INTO compra (id, costo_bruto, costo_neto, descuento_total, fecha, lote_id, proveedor_id) VALUES
(4, 15200.00, 14592.00, 608.00, '2025-10-12 11:20:00', 4, 1);

-- Compra 5: Lote 5 - Octubre
INSERT INTO compra (id, costo_bruto, costo_neto, descuento_total, fecha, lote_id, proveedor_id) VALUES
(5, 9500.00, 9120.00, 380.00, '2025-10-20 15:30:00', 5, 4);

-- Compra 6: Lote 6 - Octubre
INSERT INTO compra (id, costo_bruto, costo_neto, descuento_total, fecha, lote_id, proveedor_id) VALUES
(6, 11000.00, 10560.00, 440.00, '2025-10-28 10:00:00', 6, 2);

-- Compra 7: Lote 7 - Noviembre
INSERT INTO compra (id, costo_bruto, costo_neto, descuento_total, fecha, lote_id, proveedor_id) VALUES
(7, 13500.00, 12960.00, 540.00, '2025-11-05 13:45:00', 7, 5);

-- Compra 8: Lote 8 - Noviembre
INSERT INTO compra (id, costo_bruto, costo_neto, descuento_total, fecha, lote_id, proveedor_id) VALUES
(8, 7800.00, 7488.00, 312.00, '2025-11-12 16:30:00', 8, 3);

-- ========================================
-- 4. DETALLES DE COMPRA (32 registros - 4 por compra)
-- ========================================

-- Detalles Compra 1
INSERT INTO detalle_compra (id, cantidad, costo_unitario, descuento, subtotal, fecha_vencimiento, compra_id, producto_id) VALUES
(1, 20, 180.00, 100.00, 3500.00, '2027-09-15', 1, 1),
(2, 15, 220.00, 150.00, 3150.00, '2027-09-15', 1, 2),
(3, 30, 35.00, 75.00, 975.00, '2026-09-15', 1, 3),
(4, 25, 18.00, 100.00, 350.00, '2026-09-15', 1, 4);

-- Detalles Compra 2
INSERT INTO detalle_compra (id, cantidad, costo_unitario, descuento, subtotal, fecha_vencimiento, compra_id, producto_id) VALUES
(5, 25, 180.00, 200.00, 4300.00, '2027-09-28', 2, 1),
(6, 20, 220.00, 150.00, 4250.00, '2027-09-28', 2, 2),
(7, 35, 35.00, 125.00, 1100.00, '2026-09-28', 2, 3),
(8, 40, 18.00, 125.00, 595.00, '2026-09-28', 2, 4);

-- Detalles Compra 3
INSERT INTO detalle_compra (id, cantidad, costo_unitario, descuento, subtotal, fecha_vencimiento, compra_id, producto_id) VALUES
(9, 15, 180.00, 54.00, 2646.00, '2027-10-05', 3, 1),
(10, 12, 220.00, 66.00, 2574.00, '2027-10-05', 3, 2),
(11, 25, 35.00, 42.00, 833.00, '2026-10-05', 3, 3),
(12, 30, 18.00, 42.00, 498.00, '2026-10-05', 3, 4);

-- Detalles Compra 4
INSERT INTO detalle_compra (id, cantidad, costo_unitario, descuento, subtotal, fecha_vencimiento, compra_id, producto_id) VALUES
(13, 30, 180.00, 180.00, 5220.00, '2027-10-12', 4, 1),
(14, 25, 220.00, 220.00, 5280.00, '2027-10-12', 4, 2),
(15, 40, 35.00, 100.00, 1300.00, '2026-10-12', 4, 3),
(16, 50, 18.00, 108.00, 792.00, '2026-10-12', 4, 4);

-- Detalles Compra 5
INSERT INTO detalle_compra (id, cantidad, costo_unitario, descuento, subtotal, fecha_vencimiento, compra_id, producto_id) VALUES
(17, 18, 180.00, 120.00, 3120.00, '2027-10-20', 5, 1),
(18, 16, 220.00, 110.00, 3410.00, '2027-10-20', 5, 2),
(19, 28, 35.00, 70.00, 910.00, '2026-10-20', 5, 3),
(20, 35, 18.00, 80.00, 550.00, '2026-10-20', 5, 4);

-- Detalles Compra 6
INSERT INTO detalle_compra (id, cantidad, costo_unitario, descuento, subtotal, fecha_vencimiento, compra_id, producto_id) VALUES
(21, 22, 180.00, 140.00, 3820.00, '2027-10-28', 6, 1),
(22, 18, 220.00, 130.00, 3830.00, '2027-10-28', 6, 2),
(23, 32, 35.00, 80.00, 1040.00, '2026-10-28', 6, 3),
(24, 38, 18.00, 90.00, 594.00, '2026-10-28', 6, 4);

-- Detalles Compra 7
INSERT INTO detalle_compra (id, cantidad, costo_unitario, descuento, subtotal, fecha_vencimiento, compra_id, producto_id) VALUES
(25, 28, 180.00, 180.00, 4860.00, '2027-11-05', 7, 1),
(26, 22, 220.00, 165.00, 4675.00, '2027-11-05', 7, 2),
(27, 36, 35.00, 95.00, 1165.00, '2026-11-05', 7, 3),
(28, 42, 18.00, 100.00, 656.00, '2026-11-05', 7, 4);

-- Detalles Compra 8
INSERT INTO detalle_compra (id, cantidad, costo_unitario, descuento, subtotal, fecha_vencimiento, compra_id, producto_id) VALUES
(29, 16, 180.00, 96.00, 2784.00, '2027-11-12', 8, 1),
(30, 14, 220.00, 88.00, 2992.00, '2027-11-12', 8, 2),
(31, 26, 35.00, 62.00, 848.00, '2026-11-12', 8, 3),
(32, 32, 18.00, 66.00, 510.00, '2026-11-12', 8, 4);

-- ========================================
-- 5. SUBLOTES (32 registros - uno por detalle de compra)
-- ========================================

-- Sublotes de Lote 1
INSERT INTO sublote (id, fecha_vencimiento, fecha_produccion, codigo_sublote, cantidad_inicial, cantidad_actual, costo_unitario, estado, lote_id, producto_id, detalle_compra_id) VALUES
(1, '2027-09-15', '2025-03-15', 'SL-001-2025', 20.00, 8.50, 180.00, 'DISPONIBLE', 1, 1, 1),
(2, '2027-09-15', '2025-03-15', 'SL-002-2025', 15.00, 5.25, 220.00, 'DISPONIBLE', 1, 2, 2),
(3, '2026-09-15', '2025-06-15', 'SL-003-2025', 30.00, 12.00, 35.00, 'DISPONIBLE', 1, 3, 3),
(4, '2026-09-15', '2025-06-15', 'SL-004-2025', 25.00, 10.50, 18.00, 'DISPONIBLE', 1, 4, 4);

-- Sublotes de Lote 2
INSERT INTO sublote (id, fecha_vencimiento, fecha_produccion, codigo_sublote, cantidad_inicial, cantidad_actual, costo_unitario, estado, lote_id, producto_id, detalle_compra_id) VALUES
(5, '2027-09-28', '2025-03-28', 'SL-005-2025', 25.00, 10.00, 180.00, 'DISPONIBLE', 2, 1, 5),
(6, '2027-09-28', '2025-03-28', 'SL-006-2025', 20.00, 7.80, 220.00, 'DISPONIBLE', 2, 2, 6),
(7, '2026-09-28', '2025-06-28', 'SL-007-2025', 35.00, 14.20, 35.00, 'DISPONIBLE', 2, 3, 7),
(8, '2026-09-28', '2025-06-28', 'SL-008-2025', 40.00, 16.00, 18.00, 'DISPONIBLE', 2, 4, 8);

-- Sublotes de Lote 3
INSERT INTO sublote (id, fecha_vencimiento, fecha_produccion, codigo_sublote, cantidad_inicial, cantidad_actual, costo_unitario, estado, lote_id, producto_id, detalle_compra_id) VALUES
(9, '2027-10-05', '2025-04-05', 'SL-009-2025', 15.00, 6.50, 180.00, 'DISPONIBLE', 3, 1, 9),
(10, '2027-10-05', '2025-04-05', 'SL-010-2025', 12.00, 5.20, 220.00, 'DISPONIBLE', 3, 2, 10),
(11, '2026-10-05', '2025-07-05', 'SL-011-2025', 25.00, 11.00, 35.00, 'DISPONIBLE', 3, 3, 11),
(12, '2026-10-05', '2025-07-05', 'SL-012-2025', 30.00, 13.50, 18.00, 'DISPONIBLE', 3, 4, 12);

-- Sublotes de Lote 4
INSERT INTO sublote (id, fecha_vencimiento, fecha_produccion, codigo_sublote, cantidad_inicial, cantidad_actual, costo_unitario, estado, lote_id, producto_id, detalle_compra_id) VALUES
(13, '2027-10-12', '2025-04-12', 'SL-013-2025', 30.00, 14.00, 180.00, 'DISPONIBLE', 4, 1, 13),
(14, '2027-10-12', '2025-04-12', 'SL-014-2025', 25.00, 12.25, 220.00, 'DISPONIBLE', 4, 2, 14),
(15, '2026-10-12', '2025-07-12', 'SL-015-2025', 40.00, 19.00, 35.00, 'DISPONIBLE', 4, 3, 15),
(16, '2026-10-12', '2025-07-12', 'SL-016-2025', 50.00, 24.50, 18.00, 'DISPONIBLE', 4, 4, 16);

-- Sublotes de Lote 5
INSERT INTO sublote (id, fecha_vencimiento, fecha_produccion, codigo_sublote, cantidad_inicial, cantidad_actual, costo_unitario, estado, lote_id, producto_id, detalle_compra_id) VALUES
(17, '2027-10-20', '2025-04-20', 'SL-017-2025', 18.00, 8.75, 180.00, 'DISPONIBLE', 5, 1, 17),
(18, '2027-10-20', '2025-04-20', 'SL-018-2025', 16.00, 7.80, 220.00, 'DISPONIBLE', 5, 2, 18),
(19, '2026-10-20', '2025-07-20', 'SL-019-2025', 28.00, 13.80, 35.00, 'DISPONIBLE', 5, 3, 19),
(20, '2026-10-20', '2025-07-20', 'SL-020-2025', 35.00, 17.25, 18.00, 'DISPONIBLE', 5, 4, 20);

-- Sublotes de Lote 6
INSERT INTO sublote (id, fecha_vencimiento, fecha_produccion, codigo_sublote, cantidad_inicial, cantidad_actual, costo_unitario, estado, lote_id, producto_id, detalle_compra_id) VALUES
(21, '2027-10-28', '2025-04-28', 'SL-021-2025', 22.00, 11.00, 180.00, 'DISPONIBLE', 6, 1, 21),
(22, '2027-10-28', '2025-04-28', 'SL-022-2025', 18.00, 9.20, 220.00, 'DISPONIBLE', 6, 2, 22),
(23, '2026-10-28', '2025-07-28', 'SL-023-2025', 32.00, 16.20, 35.00, 'DISPONIBLE', 6, 3, 23),
(24, '2026-10-28', '2025-07-28', 'SL-024-2025', 38.00, 19.50, 18.00, 'DISPONIBLE', 6, 4, 24);

-- Sublotes de Lote 7
INSERT INTO sublote (id, fecha_vencimiento, fecha_produccion, codigo_sublote, cantidad_inicial, cantidad_actual, costo_unitario, estado, lote_id, producto_id, detalle_compra_id) VALUES
(25, '2027-11-05', '2025-05-05', 'SL-025-2025', 28.00, 16.50, 180.00, 'DISPONIBLE', 7, 1, 25),
(26, '2027-11-05', '2025-05-05', 'SL-026-2025', 22.00, 13.75, 220.00, 'DISPONIBLE', 7, 2, 26),
(27, '2026-11-05', '2025-08-05', 'SL-027-2025', 36.00, 21.50, 35.00, 'DISPONIBLE', 7, 3, 27),
(28, '2026-11-05', '2025-08-05', 'SL-028-2025', 42.00, 25.00, 18.00, 'DISPONIBLE', 7, 4, 28);

-- Sublotes de Lote 8
INSERT INTO sublote (id, fecha_vencimiento, fecha_produccion, codigo_sublote, cantidad_inicial, cantidad_actual, costo_unitario, estado, lote_id, producto_id, detalle_compra_id) VALUES
(29, '2027-11-12', '2025-05-12', 'SL-029-2025', 16.00, 12.00, 180.00, 'DISPONIBLE', 8, 1, 29),
(30, '2027-11-12', '2025-05-12', 'SL-030-2025', 14.00, 10.50, 220.00, 'DISPONIBLE', 8, 2, 30),
(31, '2026-11-12', '2025-08-12', 'SL-031-2025', 26.00, 19.50, 35.00, 'DISPONIBLE', 8, 3, 31),
(32, '2026-11-12', '2025-08-12', 'SL-032-2025', 32.00, 24.00, 18.00, 'DISPONIBLE', 8, 4, 32);

-- ========================================
-- 6. PRECIOS HISTÓRICOS (evolución de precios)
-- ========================================

-- Producto 1: Chanel No. 5 (precio aumenta gradualmente)
INSERT INTO precios_historico (id, precio_venta, fecha, producto_id) VALUES
(1, 450.00, '2025-09-01 08:00:00', 1),
(2, 460.00, '2025-10-01 08:00:00', 1),
(3, 475.00, '2025-11-01 08:00:00', 1);

-- Producto 2: Dior Sauvage
INSERT INTO precios_historico (id, precio_venta, fecha, producto_id) VALUES
(4, 520.00, '2025-09-01 08:00:00', 2),
(5, 530.00, '2025-10-01 08:00:00', 2),
(6, 545.00, '2025-11-01 08:00:00', 2);

-- Producto 3: Crema Nivea
INSERT INTO precios_historico (id, precio_venta, fecha, producto_id) VALUES
(7, 85.00, '2025-09-01 08:00:00', 3),
(8, 88.00, '2025-10-01 08:00:00', 3),
(9, 92.00, '2025-11-01 08:00:00', 3);

-- Producto 4: Pantene Pro-V
INSERT INTO precios_historico (id, precio_venta, fecha, producto_id) VALUES
(10, 42.00, '2025-09-01 08:00:00', 4),
(11, 44.00, '2025-10-01 08:00:00', 4),
(12, 45.00, '2025-11-01 08:00:00', 4);

-- ========================================
-- 7. VENTAS (40 registros distribuidos en 3 meses)
-- ========================================

-- Ventas de Septiembre (10 ventas)
INSERT INTO venta (id, fecha, total_bruto, descuento_total, total_neto, con_factura, metodo_de_pago_id, cliente_id) VALUES
(1, '2025-09-16 10:30:00', 1870.00, 0.00, 1870.00, true, 1, 1),
(2, '2025-09-17 14:45:00', 960.00, 50.00, 910.00, false, 2, 2),
(3, '2025-09-19 11:20:00', 1390.00, 0.00, 1390.00, true, 1, 3),
(4, '2025-09-21 16:15:00', 544.00, 20.00, 524.00, false, 3, 1),
(5, '2025-09-23 09:40:00', 2630.00, 80.00, 2550.00, true, 2, 2),
(6, '2025-09-25 13:25:00', 720.00, 0.00, 720.00, false, 1, 3),
(7, '2025-09-26 15:50:00', 1560.00, 60.00, 1500.00, true, 2, 1),
(8, '2025-09-28 10:10:00', 890.00, 40.00, 850.00, false, 1, 2),
(9, '2025-09-29 12:30:00', 1740.00, 0.00, 1740.00, true, 3, 3),
(10, '2025-09-30 17:00:00', 628.00, 28.00, 600.00, false, 1, 1);

-- Ventas de Octubre (20 ventas)
INSERT INTO venta (id, fecha, total_bruto, descuento_total, total_neto, con_factura, metodo_de_pago_id, cliente_id) VALUES
(11, '2025-10-01 09:15:00', 1060.00, 0.00, 1060.00, true, 1, 2),
(12, '2025-10-02 11:40:00', 1590.00, 90.00, 1500.00, true, 2, 3),
(13, '2025-10-03 14:20:00', 920.00, 20.00, 900.00, false, 1, 1),
(14, '2025-10-05 10:50:00', 2210.00, 110.00, 2100.00, true, 3, 2),
(15, '2025-10-07 15:30:00', 660.00, 0.00, 660.00, false, 1, 3),
(16, '2025-10-09 09:00:00', 1840.00, 40.00, 1800.00, true, 2, 1),
(17, '2025-10-11 13:45:00', 1320.00, 0.00, 1320.00, false, 1, 2),
(18, '2025-10-13 16:20:00', 2780.00, 180.00, 2600.00, true, 3, 3),
(19, '2025-10-15 10:30:00', 544.00, 24.00, 520.00, false, 1, 1),
(20, '2025-10-17 12:15:00', 1925.00, 75.00, 1850.00, true, 2, 2),
(21, '2025-10-19 14:50:00', 880.00, 0.00, 880.00, false, 1, 3),
(22, '2025-10-21 09:30:00', 1480.00, 80.00, 1400.00, true, 2, 1),
(23, '2025-10-23 11:00:00', 792.00, 42.00, 750.00, false, 1, 2),
(24, '2025-10-25 15:15:00', 2340.00, 140.00, 2200.00, true, 3, 3),
(25, '2025-10-27 10:45:00', 1056.00, 56.00, 1000.00, false, 1, 1),
(26, '2025-10-28 13:20:00', 1760.00, 60.00, 1700.00, true, 2, 2),
(27, '2025-10-29 16:40:00', 968.00, 68.00, 900.00, false, 1, 3),
(28, '2025-10-30 09:50:00', 2090.00, 90.00, 2000.00, true, 3, 1),
(29, '2025-10-31 12:25:00', 616.00, 16.00, 600.00, false, 1, 2),
(30, '2025-10-31 17:10:00', 1485.00, 0.00, 1485.00, true, 2, 3);

-- Ventas de Noviembre (10 ventas)
INSERT INTO venta (id, fecha, total_bruto, descuento_total, total_neto, con_factura, metodo_de_pago_id, cliente_id) VALUES
(31, '2025-11-01 10:20:00', 1900.00, 0.00, 1900.00, true, 1, 1),
(32, '2025-11-03 13:45:00', 1272.00, 72.00, 1200.00, false, 2, 2),
(33, '2025-11-05 09:30:00', 2375.00, 175.00, 2200.00, true, 3, 3),
(34, '2025-11-07 11:50:00', 820.00, 20.00, 800.00, false, 1, 1),
(35, '2025-11-09 14:15:00', 1635.00, 135.00, 1500.00, true, 2, 2),
(36, '2025-11-11 10:00:00', 990.00, 0.00, 990.00, false, 1, 3),
(37, '2025-11-13 15:30:00', 2180.00, 80.00, 2100.00, true, 3, 1),
(38, '2025-11-15 12:40:00', 736.00, 36.00, 700.00, false, 1, 2),
(39, '2025-11-17 09:15:00', 1845.00, 45.00, 1800.00, true, 2, 3),
(40, '2025-11-18 16:20:00', 1092.00, 92.00, 1000.00, false, 1, 1);

-- ========================================
-- 8. DETALLES DE VENTA (variando cantidades)
-- ========================================

-- Detalles Venta 1
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(1, 2, 900.00, 0.00, 1, 1),
(2, 1, 520.00, 0.00, 2, 1),
(3, 5, 425.00, 0.00, 3, 1),
(4, 1, 42.00, 0.00, 4, 1);

-- Detalles Venta 2
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(5, 1, 450.00, 0.00, 1, 2),
(6, 1, 520.00, 50.00, 2, 2);

-- Detalles Venta 3
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(7, 3, 1350.00, 0.00, 1, 3),
(8, 1, 42.00, 0.00, 4, 3);

-- Detalles Venta 4
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(9, 6, 510.00, 0.00, 3, 4),
(10, 1, 42.00, 20.00, 4, 4);

-- Detalles Venta 5
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(11, 3, 1350.00, 0.00, 1, 5),
(12, 2, 1040.00, 0.00, 2, 5),
(13, 3, 255.00, 45.00, 3, 5),
(14, 2, 84.00, 35.00, 4, 5);

-- Detalles Venta 6
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(15, 8, 680.00, 0.00, 3, 6),
(16, 1, 42.00, 0.00, 4, 6);

-- Detalles Venta 7
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(17, 2, 900.00, 0.00, 1, 7),
(18, 1, 520.00, 0.00, 2, 7),
(19, 2, 170.00, 60.00, 3, 7);

-- Detalles Venta 8
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(20, 1, 450.00, 0.00, 1, 8),
(21, 5, 425.00, 40.00, 3, 8);

-- Detalles Venta 9
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(22, 2, 900.00, 0.00, 1, 9),
(23, 10, 850.00, 0.00, 3, 9);

-- Detalles Venta 10
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(24, 1, 450.00, 0.00, 1, 10),
(25, 2, 170.00, 0.00, 3, 10),
(26, 2, 84.00, 28.00, 4, 10);

-- Detalles Venta 11
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(27, 2, 920.00, 0.00, 1, 11),
(28, 2, 176.00, 0.00, 3, 11);

-- Detalles Venta 12
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(29, 3, 1380.00, 0.00, 1, 12),
(30, 3, 264.00, 90.00, 3, 12);

-- Detalles Venta 13
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(31, 2, 920.00, 20.00, 1, 13),
(32, 1, 44.00, 0.00, 4, 13);

-- Detalles Venta 14
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(33, 2, 920.00, 0.00, 1, 14),
(34, 2, 1060.00, 0.00, 2, 14),
(35, 3, 264.00, 110.00, 3, 14);

-- Detalles Venta 15
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(36, 7, 616.00, 0.00, 3, 15),
(37, 1, 44.00, 0.00, 4, 15);

-- Detalles Venta 16
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(38, 4, 1840.00, 40.00, 1, 16),
(39, 1, 44.00, 0.00, 4, 16);

-- Detalles Venta 17
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(40, 1, 460.00, 0.00, 1, 17),
(41, 10, 880.00, 0.00, 3, 17);

-- Detalles Venta 18
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(42, 3, 1380.00, 0.00, 1, 18),
(43, 2, 1060.00, 0.00, 2, 18),
(44, 4, 352.00, 0.00, 3, 18),
(45, 2, 88.00, 180.00, 4, 18);

-- Detalles Venta 19
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(46, 6, 528.00, 0.00, 3, 19),
(47, 1, 44.00, 24.00, 4, 19);

-- Detalles Venta 20
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(48, 2, 920.00, 0.00, 1, 20),
(49, 1, 530.00, 0.00, 2, 20),
(50, 5, 440.00, 75.00, 3, 20);

-- Detalles Venta 21
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(51, 10, 880.00, 0.00, 3, 21),
(52, 1, 44.00, 0.00, 4, 21);

-- Detalles Venta 22
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(53, 2, 920.00, 0.00, 1, 22),
(54, 1, 530.00, 0.00, 2, 22),
(55, 1, 88.00, 80.00, 3, 22);

-- Detalles Venta 23
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(56, 1, 460.00, 0.00, 1, 23),
(57, 4, 352.00, 42.00, 3, 23);

-- Detalles Venta 24
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(58, 3, 1380.00, 0.00, 1, 24),
(59, 1, 530.00, 0.00, 2, 24),
(60, 5, 440.00, 0.00, 3, 24),
(61, 2, 88.00, 140.00, 4, 24);

-- Detalles Venta 25
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(62, 2, 920.00, 0.00, 1, 25),
(63, 2, 176.00, 56.00, 3, 25);

-- Detalles Venta 26
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(64, 2, 920.00, 0.00, 1, 26),
(65, 1, 530.00, 0.00, 2, 26),
(66, 3, 264.00, 0.00, 3, 26),
(67, 2, 88.00, 60.00, 4, 26);

-- Detalles Venta 27
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(68, 1, 460.00, 0.00, 1, 27),
(69, 1, 530.00, 68.00, 2, 27);

-- Detalles Venta 28
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(70, 2, 920.00, 0.00, 1, 28),
(71, 2, 1060.00, 0.00, 2, 28),
(72, 3, 264.00, 90.00, 3, 28);

-- Detalles Venta 29
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(73, 1, 460.00, 0.00, 1, 29),
(74, 2, 176.00, 16.00, 3, 29);

-- Detalles Venta 30
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(75, 3, 1380.00, 0.00, 1, 30),
(76, 2, 176.00, 0.00, 3, 30);

-- Detalles Venta 31
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(77, 4, 1900.00, 0.00, 1, 31),
(78, 1, 45.00, 0.00, 4, 31);

-- Detalles Venta 32
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(79, 2, 950.00, 0.00, 1, 32),
(80, 4, 368.00, 72.00, 3, 32);

-- Detalles Venta 33
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(81, 3, 1425.00, 0.00, 1, 33),
(82, 1, 545.00, 0.00, 2, 33),
(83, 5, 460.00, 175.00, 3, 33);

-- Detalles Venta 34
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(84, 9, 828.00, 0.00, 3, 34),
(85, 1, 45.00, 20.00, 4, 34);

-- Detalles Venta 35
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(86, 2, 950.00, 0.00, 1, 35),
(87, 1, 545.00, 0.00, 2, 35),
(88, 2, 184.00, 135.00, 3, 35);

-- Detalles Venta 36
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(89, 2, 950.00, 0.00, 1, 36),
(90, 1, 45.00, 0.00, 4, 36);

-- Detalles Venta 37
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(91, 3, 1425.00, 0.00, 1, 37),
(92, 1, 545.00, 0.00, 2, 37),
(93, 3, 276.00, 80.00, 3, 37);

-- Detalles Venta 38
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(94, 1, 475.00, 0.00, 1, 38),
(95, 3, 276.00, 0.00, 3, 38),
(96, 1, 45.00, 36.00, 4, 38);

-- Detalles Venta 39
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(97, 2, 950.00, 0.00, 1, 39),
(98, 1, 545.00, 0.00, 2, 39),
(99, 4, 368.00, 45.00, 3, 39);

-- Detalles Venta 40
INSERT INTO detalle_venta (id, cantidad, subtotal, descuento, producto_id, venta_id) VALUES
(100, 2, 950.00, 0.00, 1, 40),
(101, 2, 184.00, 92.00, 3, 40);

-- ========================================
-- 9. MOVIMIENTOS DE INVENTARIO (40 movimientos de ventas)
-- ========================================

-- Movimientos para Venta 1
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(1, 2.00, 180.00, 450.00, 360.00, 900.00, '2025-09-16 10:30:00', 'VENTA', 1, 1),
(2, 1.00, 220.00, 520.00, 220.00, 520.00, '2025-09-16 10:30:00', 'VENTA', 2, 1),
(3, 5.00, 35.00, 85.00, 175.00, 425.00, '2025-09-16 10:30:00', 'VENTA', 3, 1),
(4, 1.00, 18.00, 42.00, 18.00, 42.00, '2025-09-16 10:30:00', 'VENTA', 4, 1);

-- Movimientos para Venta 2
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(5, 1.00, 180.00, 450.00, 180.00, 450.00, '2025-09-17 14:45:00', 'VENTA', 5, 2),
(6, 1.00, 220.00, 520.00, 220.00, 520.00, '2025-09-17 14:45:00', 'VENTA', 6, 2);

-- Movimientos para Venta 3
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(7, 3.00, 180.00, 450.00, 540.00, 1350.00, '2025-09-19 11:20:00', 'VENTA', 7, 3),
(8, 1.00, 18.00, 42.00, 18.00, 42.00, '2025-09-19 11:20:00', 'VENTA', 8, 3);

-- Movimientos para Venta 4
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(9, 6.00, 35.00, 85.00, 210.00, 510.00, '2025-09-21 16:15:00', 'VENTA', 9, 4),
(10, 1.00, 18.00, 42.00, 18.00, 42.00, '2025-09-21 16:15:00', 'VENTA', 10, 4);

-- Movimientos para Venta 5
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(11, 3.00, 180.00, 450.00, 540.00, 1350.00, '2025-09-23 09:40:00', 'VENTA', 11, 5),
(12, 2.00, 220.00, 520.00, 440.00, 1040.00, '2025-09-23 09:40:00', 'VENTA', 12, 5),
(13, 3.00, 35.00, 85.00, 105.00, 255.00, '2025-09-23 09:40:00', 'VENTA', 13, 5),
(14, 2.00, 18.00, 42.00, 36.00, 84.00, '2025-09-23 09:40:00', 'VENTA', 14, 5);

-- Movimientos para Venta 6
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(15, 8.00, 35.00, 85.00, 280.00, 680.00, '2025-09-25 13:25:00', 'VENTA', 15, 6),
(16, 1.00, 18.00, 42.00, 18.00, 42.00, '2025-09-25 13:25:00', 'VENTA', 16, 6);

-- Movimientos para Venta 7
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(17, 2.00, 180.00, 450.00, 360.00, 900.00, '2025-09-26 15:50:00', 'VENTA', 17, 7),
(18, 1.00, 220.00, 520.00, 220.00, 520.00, '2025-09-26 15:50:00', 'VENTA', 18, 7),
(19, 2.00, 35.00, 85.00, 70.00, 170.00, '2025-09-26 15:50:00', 'VENTA', 19, 7);

-- Movimientos para Venta 8
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(20, 1.00, 180.00, 450.00, 180.00, 450.00, '2025-09-28 10:10:00', 'VENTA', 20, 8),
(21, 5.00, 35.00, 85.00, 175.00, 425.00, '2025-09-28 10:10:00', 'VENTA', 21, 8);

-- Movimientos para Venta 9
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(22, 2.00, 180.00, 450.00, 360.00, 900.00, '2025-09-29 12:30:00', 'VENTA', 22, 9),
(23, 10.00, 35.00, 85.00, 350.00, 850.00, '2025-09-29 12:30:00', 'VENTA', 23, 9);

-- Movimientos para Venta 10
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(24, 1.00, 180.00, 450.00, 180.00, 450.00, '2025-09-30 17:00:00', 'VENTA', 24, 10),
(25, 2.00, 35.00, 85.00, 70.00, 170.00, '2025-09-30 17:00:00', 'VENTA', 25, 10),
(26, 2.00, 18.00, 42.00, 36.00, 84.00, '2025-09-30 17:00:00', 'VENTA', 26, 10);

-- Movimientos para Venta 11
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(27, 2.00, 180.00, 460.00, 360.00, 920.00, '2025-10-01 09:15:00', 'VENTA', 27, 11),
(28, 2.00, 35.00, 88.00, 70.00, 176.00, '2025-10-01 09:15:00', 'VENTA', 28, 11);

-- Movimientos para Venta 12
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(29, 3.00, 180.00, 460.00, 540.00, 1380.00, '2025-10-02 11:40:00', 'VENTA', 29, 12),
(30, 3.00, 35.00, 88.00, 105.00, 264.00, '2025-10-02 11:40:00', 'VENTA', 30, 12);

-- Movimientos para Venta 13
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(31, 2.00, 180.00, 460.00, 360.00, 920.00, '2025-10-03 14:20:00', 'VENTA', 31, 13),
(32, 1.00, 18.00, 44.00, 18.00, 44.00, '2025-10-03 14:20:00', 'VENTA', 32, 13);

-- Movimientos para Venta 14
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(33, 2.00, 180.00, 460.00, 360.00, 920.00, '2025-10-05 10:50:00', 'VENTA', 33, 14),
(34, 2.00, 220.00, 530.00, 440.00, 1060.00, '2025-10-05 10:50:00', 'VENTA', 34, 14),
(35, 3.00, 35.00, 88.00, 105.00, 264.00, '2025-10-05 10:50:00', 'VENTA', 35, 14);

-- Movimientos para Venta 15
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(36, 7.00, 35.00, 88.00, 245.00, 616.00, '2025-10-07 15:30:00', 'VENTA', 36, 15),
(37, 1.00, 18.00, 44.00, 18.00, 44.00, '2025-10-07 15:30:00', 'VENTA', 37, 15);

-- Movimientos para Venta 16
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(38, 4.00, 180.00, 460.00, 720.00, 1840.00, '2025-10-09 09:00:00', 'VENTA', 38, 16),
(39, 1.00, 18.00, 44.00, 18.00, 44.00, '2025-10-09 09:00:00', 'VENTA', 39, 16);

-- Movimientos para Venta 17
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(40, 1.00, 180.00, 460.00, 180.00, 460.00, '2025-10-11 13:45:00', 'VENTA', 40, 17),
(41, 10.00, 35.00, 88.00, 350.00, 880.00, '2025-10-11 13:45:00', 'VENTA', 41, 17);

-- Movimientos para Venta 18
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(42, 3.00, 180.00, 460.00, 540.00, 1380.00, '2025-10-13 16:20:00', 'VENTA', 42, 18),
(43, 2.00, 220.00, 530.00, 440.00, 1060.00, '2025-10-13 16:20:00', 'VENTA', 43, 18),
(44, 4.00, 35.00, 88.00, 140.00, 352.00, '2025-10-13 16:20:00', 'VENTA', 44, 18),
(45, 2.00, 18.00, 44.00, 36.00, 88.00, '2025-10-13 16:20:00', 'VENTA', 45, 18);

-- Movimientos para Venta 19
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(46, 6.00, 35.00, 88.00, 210.00, 528.00, '2025-10-15 10:30:00', 'VENTA', 46, 19),
(47, 1.00, 18.00, 44.00, 18.00, 44.00, '2025-10-15 10:30:00', 'VENTA', 47, 19);

-- Movimientos para Venta 20
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(48, 2.00, 180.00, 460.00, 360.00, 920.00, '2025-10-17 12:15:00', 'VENTA', 48, 20),
(49, 1.00, 220.00, 530.00, 220.00, 530.00, '2025-10-17 12:15:00', 'VENTA', 49, 20),
(50, 5.00, 35.00, 88.00, 175.00, 440.00, '2025-10-17 12:15:00', 'VENTA', 50, 20);

-- Movimientos para Venta 21
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(51, 10.00, 35.00, 88.00, 350.00, 880.00, '2025-10-19 14:50:00', 'VENTA', 51, 21),
(52, 1.00, 18.00, 44.00, 18.00, 44.00, '2025-10-19 14:50:00', 'VENTA', 52, 21);

-- Movimientos para Venta 22
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(53, 2.00, 180.00, 460.00, 360.00, 920.00, '2025-10-21 09:30:00', 'VENTA', 53, 22),
(54, 1.00, 220.00, 530.00, 220.00, 530.00, '2025-10-21 09:30:00', 'VENTA', 54, 22),
(55, 1.00, 35.00, 88.00, 35.00, 88.00, '2025-10-21 09:30:00', 'VENTA', 55, 22);

-- Movimientos para Venta 23
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(56, 1.00, 180.00, 460.00, 180.00, 460.00, '2025-10-23 11:00:00', 'VENTA', 56, 23),
(57, 4.00, 35.00, 88.00, 140.00, 352.00, '2025-10-23 11:00:00', 'VENTA', 57, 23);

-- Movimientos para Venta 24
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(58, 3.00, 180.00, 460.00, 540.00, 1380.00, '2025-10-25 15:15:00', 'VENTA', 58, 24),
(59, 1.00, 220.00, 530.00, 220.00, 530.00, '2025-10-25 15:15:00', 'VENTA', 59, 24),
(60, 5.00, 35.00, 88.00, 175.00, 440.00, '2025-10-25 15:15:00', 'VENTA', 60, 24),
(61, 2.00, 18.00, 44.00, 36.00, 88.00, '2025-10-25 15:15:00', 'VENTA', 61, 24);

-- Movimientos para Venta 25
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(62, 2.00, 180.00, 460.00, 360.00, 920.00, '2025-10-27 10:45:00', 'VENTA', 62, 25),
(63, 2.00, 35.00, 88.00, 70.00, 176.00, '2025-10-27 10:45:00', 'VENTA', 63, 25);

-- Movimientos para Venta 26
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(64, 2.00, 180.00, 460.00, 360.00, 920.00, '2025-10-28 13:20:00', 'VENTA', 64, 26),
(65, 1.00, 220.00, 530.00, 220.00, 530.00, '2025-10-28 13:20:00', 'VENTA', 65, 26),
(66, 3.00, 35.00, 88.00, 105.00, 264.00, '2025-10-28 13:20:00', 'VENTA', 66, 26),
(67, 2.00, 18.00, 44.00, 36.00, 88.00, '2025-10-28 13:20:00', 'VENTA', 67, 26);

-- Movimientos para Venta 27
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(68, 1.00, 180.00, 460.00, 180.00, 460.00, '2025-10-29 16:40:00', 'VENTA', 68, 27),
(69, 1.00, 220.00, 530.00, 220.00, 530.00, '2025-10-29 16:40:00', 'VENTA', 69, 27);

-- Movimientos para Venta 28
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(70, 2.00, 180.00, 460.00, 360.00, 920.00, '2025-10-30 09:50:00', 'VENTA', 70, 28),
(71, 2.00, 220.00, 530.00, 440.00, 1060.00, '2025-10-30 09:50:00', 'VENTA', 71, 28),
(72, 3.00, 35.00, 88.00, 105.00, 264.00, '2025-10-30 09:50:00', 'VENTA', 72, 28);

-- Movimientos para Venta 29
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(73, 1.00, 180.00, 460.00, 180.00, 460.00, '2025-10-31 12:25:00', 'VENTA', 73, 29),
(74, 2.00, 35.00, 88.00, 70.00, 176.00, '2025-10-31 12:25:00', 'VENTA', 74, 29);

-- Movimientos para Venta 30
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(75, 3.00, 180.00, 460.00, 540.00, 1380.00, '2025-10-31 17:10:00', 'VENTA', 75, 30),
(76, 2.00, 35.00, 88.00, 70.00, 176.00, '2025-10-31 17:10:00', 'VENTA', 76, 30);

-- Movimientos para Venta 31
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(77, 4.00, 180.00, 475.00, 720.00, 1900.00, '2025-11-01 10:20:00', 'VENTA', 77, 31),
(78, 1.00, 18.00, 45.00, 18.00, 45.00, '2025-11-01 10:20:00', 'VENTA', 78, 31);

-- Movimientos para Venta 32
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(79, 2.00, 180.00, 475.00, 360.00, 950.00, '2025-11-03 13:45:00', 'VENTA', 79, 32),
(80, 4.00, 35.00, 92.00, 140.00, 368.00, '2025-11-03 13:45:00', 'VENTA', 80, 32);

-- Movimientos para Venta 33
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(81, 3.00, 180.00, 475.00, 540.00, 1425.00, '2025-11-05 09:30:00', 'VENTA', 81, 33),
(82, 1.00, 220.00, 545.00, 220.00, 545.00, '2025-11-05 09:30:00', 'VENTA', 82, 33),
(83, 5.00, 35.00, 92.00, 175.00, 460.00, '2025-11-05 09:30:00', 'VENTA', 83, 33);

-- Movimientos para Venta 34
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(84, 9.00, 35.00, 92.00, 315.00, 828.00, '2025-11-07 11:50:00', 'VENTA', 84, 34),
(85, 1.00, 18.00, 45.00, 18.00, 45.00, '2025-11-07 11:50:00', 'VENTA', 85, 34);

-- Movimientos para Venta 35
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(86, 2.00, 180.00, 475.00, 360.00, 950.00, '2025-11-09 14:15:00', 'VENTA', 86, 35),
(87, 1.00, 220.00, 545.00, 220.00, 545.00, '2025-11-09 14:15:00', 'VENTA', 87, 35),
(88, 2.00, 35.00, 92.00, 70.00, 184.00, '2025-11-09 14:15:00', 'VENTA', 88, 35);

-- Movimientos para Venta 36
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(89, 2.00, 180.00, 475.00, 360.00, 950.00, '2025-11-11 10:00:00', 'VENTA', 89, 36),
(90, 1.00, 18.00, 45.00, 18.00, 45.00, '2025-11-11 10:00:00', 'VENTA', 90, 36);

-- Movimientos para Venta 37
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(91, 3.00, 180.00, 475.00, 540.00, 1425.00, '2025-11-13 15:30:00', 'VENTA', 91, 37),
(92, 1.00, 220.00, 545.00, 220.00, 545.00, '2025-11-13 15:30:00', 'VENTA', 92, 37),
(93, 3.00, 35.00, 92.00, 105.00, 276.00, '2025-11-13 15:30:00', 'VENTA', 93, 37);

-- Movimientos para Venta 38
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(94, 1.00, 180.00, 475.00, 180.00, 475.00, '2025-11-15 12:40:00', 'VENTA', 94, 38),
(95, 3.00, 35.00, 92.00, 105.00, 276.00, '2025-11-15 12:40:00', 'VENTA', 95, 38),
(96, 1.00, 18.00, 45.00, 18.00, 45.00, '2025-11-15 12:40:00', 'VENTA', 96, 38);

-- Movimientos para Venta 39
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(97, 2.00, 180.00, 475.00, 360.00, 950.00, '2025-11-17 09:15:00', 'VENTA', 97, 39),
(98, 1.00, 220.00, 545.00, 220.00, 545.00, '2025-11-17 09:15:00', 'VENTA', 98, 39),
(99, 4.00, 35.00, 92.00, 140.00, 368.00, '2025-11-17 09:15:00', 'VENTA', 99, 39);

-- Movimientos para Venta 40
INSERT INTO movimiento (id, cantidad, costo_unitario, precio_unitario, costo_total, precio_total, fecha, referencia_tipo, detalle_venta_id, referencia_id) VALUES
(100, 2.00, 180.00, 475.00, 360.00, 950.00, '2025-11-18 16:20:00', 'VENTA', 100, 40),
(101, 2.00, 35.00, 92.00, 70.00, 184.00, '2025-11-18 16:20:00', 'VENTA', 101, 40);

-- ========================================
-- RESUMEN DE DATOS GENERADOS
-- ========================================
-- Proveedores: 5
-- Lotes: 8
-- Compras: 8 (distribuidas en 3 meses: Sept-Nov 2025)
-- Detalles de Compra: 32 (4 productos por compra)
-- Sublotes: 32 (con cantidades actuales reducidas simulando ventas)
-- Precios Históricos: 12 (evolución mensual de 4 productos)
-- Ventas: 40 (Septiembre: 10, Octubre: 20, Noviembre: 10)
-- Detalles de Venta: 101 (promedio 2.5 productos por venta)
-- Movimientos: 101 (registro completo de trazabilidad)
-- 
-- ========================================
-- MÉTRICAS DISPONIBLES PARA DASHBOARD
-- ========================================
-- 
-- 1. VENTAS Y FACTURACIÓN:
--    - Total ventas por mes (Bs)
--    - Número de transacciones por mes
--    - Ticket promedio por venta
--    - Ventas con/sin factura
--    - Tendencia de crecimiento mensual
-- 
-- 2. PRODUCTOS:
--    - Top 5 productos más vendidos (por cantidad y valor)
--    - Productos con mayor margen de ganancia
--    - Rotación de inventario por producto
--    - Stock actual vs stock inicial
--    - Evolución de precios históricos
-- 
-- 3. CLIENTES:
--    - Clientes más frecuentes
--    - Nivel de fidelidad predominante
--    - Distribución geográfica (por ubicación)
--    - Valor promedio por cliente
--    - Clientes que prefieren factura
-- 
-- 4. MÉTODOS DE PAGO:
--    - Preferencias de pago (efectivo, tarjeta, transferencia)
--    - Monto promedio por método de pago
--    - Distribución porcentual de métodos
-- 
-- 5. COMPRAS E INVENTARIO:
--    - Total invertido en compras por mes
--    - Proveedores principales (por volumen y monto)
--    - Costo promedio de productos
--    - Descuentos obtenidos en compras
--    - Lotes con mayor rotación
-- 
-- 6. RENTABILIDAD:
--    - Margen bruto por producto (precio venta - costo)
--    - Beneficio total por mes
--    - ROI por compra/lote
--    - Impacto de descuentos en ventas
-- 
-- 7. MOVIMIENTOS DE INVENTARIO:
--    - Trazabilidad completa producto a producto
--    - FIFO/LIFO aplicado en sublotes
--    - Productos con stock crítico
--    - Productos próximos a vencer
-- 
-- ========================================
-- DATOS DESTACADOS DEL DATASET
-- ========================================
-- 
-- Periodo de datos: Septiembre - Noviembre 2025
-- 
-- PRODUCTOS:
-- 1. Chanel No. 5 (Perfume): Precio inicial Bs 450 → Bs 475 (+5.5%)
-- 2. Dior Sauvage (Perfume): Precio inicial Bs 520 → Bs 545 (+4.8%)
-- 3. Crema Nivea: Precio inicial Bs 85 → Bs 92 (+8.2%)
-- 4. Pantene Pro-V: Precio inicial Bs 42 → Bs 45 (+7.1%)
-- 
-- INVENTARIO TOTAL COMPRADO: 736 unidades
-- INVENTARIO ACTUAL APROXIMADO: ~420 unidades (57% en stock)
-- 
-- INVERSIÓN EN COMPRAS: Bs 84,372 (costo neto)
-- VENTAS TOTALES: ~Bs 60,000 (aproximado en 3 meses)
-- 
-- ========================================
-- INSTRUCCIONES DE USO
-- ========================================
-- 
-- 1. Este script debe ejecutarse DESPUÉS del changelog de Liquibase
-- 2. Asegúrate de que existen los datos base (ubicaciones, niveles, tipos, etc.)
-- 3. Los IDs son secuenciales y no usan autoincremento
-- 4. Para producción, ajusta las secuencias si es necesario:
--    SELECT setval('proveedor_id_seq', (SELECT MAX(id) FROM proveedor));
--    SELECT setval('lote_id_seq', (SELECT MAX(id) FROM lote));
--    ... etc para cada tabla
-- 
-- 5. Para limpiar los datos de prueba:
--    DELETE FROM movimiento WHERE referencia_tipo = 'VENTA';
--    DELETE FROM detalle_venta;
--    DELETE FROM venta;
--    DELETE FROM sublote;
--    DELETE FROM detalle_compra;
--    DELETE FROM compra;
--    DELETE FROM lote;
--    DELETE FROM precios_historico;
--    DELETE FROM proveedor WHERE id <= 5;
-- 
-- ================================================================================
