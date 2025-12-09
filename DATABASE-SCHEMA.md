# Estructura de Base de Datos - Módulo de Inventario

## Diagrama de Relaciones (ERD)

```
┌─────────────────┐
│  TipoProducto   │
│                 │
│  PK: id         │
└────────┬────────┘
         │
         │ 1:N
         │
┌────────▼────────────────────┐
│       Producto              │
│                             │
│  PK: id                     │◄───────┐
│  FK: tipo_producto_id       │        │
│                             │        │
│  clasificacion_abc          │        │ N:1
│  eoq                        │        │
│  punto_reorden              │        │
│  costo_pedido               │        │
│  costo_almacenamiento       │        │
│  tiempo_entrega             │        │
│  stock_seguridad            │        │
└────────┬────────────────────┘        │
         │                             │
         │ 1:N                         │
         │                             │
┌────────▼────────────────┐   ┌────────┴────────────┐
│   PedidoSugerido        │   │      Sublote        │
│                         │   │                     │
│  PK: id                 │   │  PK: id             │
│  FK: producto_id        │   │  FK: producto_id    │
│                         │   │  FK: lote_id        │
│  cantidad_sugerida      │   │  FK: detalle_compra │
│  fecha_sugerida         │   │                     │
│  estado (ENUM)          │   │  cantidad_inicial   │
│  observacion            │   │  cantidad_actual    │
│  stock_actual_momento   │   │  costo_unitario     │
│  rop_momento            │   │  fecha_vencimiento  │
└─────────────────────────┘   │  fecha_produccion   │
                              │  codigo_sublote     │
                              │  estado (ENUM)      │
                              └──────┬──────────────┘
                                     │
                                     │ N:1
                                     │
                              ┌──────▼──────────────┐
                              │       Lote          │
                              │                     │
                              │  PK: id             │
                              │  fecha_ingreso      │
                              └─────────────────────┘

┌─────────────────────┐
│     Proveedor       │
│                     │
│  PK: id             │
│  FK: ubicacion_id   │
│                     │
│  nombre             │
│  correo             │
│  telefono           │
│  nit                │
└─────────────────────┘
```

---

## Tablas Principales

### 1. **pedido_sugerido**

**Descripción:** Almacena las sugerencias automáticas de pedidos generadas por el sistema de análisis de inventario basado en ROP, EOQ y análisis ABC.

**Columnas:**

| Columna | Tipo | Nullable | Descripción |
|---------|------|----------|-------------|
| `id` | INTEGER | NOT NULL | Clave primaria, auto-incremental |
| `producto_id` | INTEGER | NOT NULL | FK a `producto.id` |
| `cantidad_sugerida` | INTEGER | NOT NULL | Cantidad recomendada para ordenar (EOQ) |
| `fecha_sugerida` | DATE | NOT NULL | Fecha en que se generó la sugerencia |
| `estado` | VARCHAR(20) | NOT NULL | Estado del pedido: PENDIENTE, APROBADO, RECHAZADO, EJECUTADO |
| `observacion` | TEXT | NULL | Notas adicionales o razón del rechazo |
| `stock_actual_momento` | DOUBLE | NULL | Stock disponible al momento de generar la sugerencia |
| `rop_momento` | INTEGER | NULL | Punto de reorden (ROP) al momento de la sugerencia |

**Restricciones:**
- PRIMARY KEY: `id`
- FOREIGN KEY: `producto_id` REFERENCES `producto(id)`
- CHECK: `estado` IN ('PENDIENTE', 'APROBADO', 'RECHAZADO', 'EJECUTADO')

**Relaciones:**
- **N:1** con `Producto` - Un producto puede tener múltiples pedidos sugeridos

---

### 2. **producto**

**Descripción:** Catálogo principal de productos con parámetros de gestión de inventario calculados.

**Columnas:**

| Columna | Tipo | Nullable | Descripción |
|---------|------|----------|-------------|
| `id` | INTEGER | NOT NULL | Clave primaria |
| `nombre` | VARCHAR | NULL | Nombre del producto |
| `descripcion` | VARCHAR | NULL | Descripción detallada |
| `descontinuado` | BOOLEAN | NULL | Indica si el producto está descontinuado |
| `clasificacion_abc` | VARCHAR | NULL | Clasificación ABC del producto (A, B, C) |
| `costo_pedido` | DOUBLE | NULL | Costo fijo de realizar un pedido |
| `costo_almacenamiento` | DOUBLE | NULL | Costo de almacenar una unidad por año |
| `tiempo_entrega` | INTEGER | NULL | Tiempo de entrega en días (lead time) |
| `stock_seguridad` | INTEGER | NULL | Stock de seguridad calculado |
| `eoq` | INTEGER | NULL | Cantidad Económica de Pedido (Economic Order Quantity) |
| `punto_reorden` | INTEGER | NULL | Punto de Reorden (ROP) |
| `tipo_producto_id` | INTEGER | NULL | FK a `tipo_producto.id` |

**Restricciones:**
- PRIMARY KEY: `id`
- FOREIGN KEY: `tipo_producto_id` REFERENCES `tipo_producto(id)`

**Relaciones:**
- **N:1** con `TipoProducto` - Un producto pertenece a un tipo
- **1:N** con `Sublote` - Un producto puede tener múltiples sublotes
- **1:N** con `PedidoSugerido` - Un producto puede tener múltiples pedidos sugeridos
- **1:N** con `DetalleVenta`, `DetalleCompra`, `PrecioHistorico`, `DetalleReserva`

---

### 3. **lote**

**Descripción:** Representa un grupo de productos recibidos en una compra específica.

**Columnas:**

| Columna | Tipo | Nullable | Descripción |
|---------|------|----------|-------------|
| `id` | INTEGER | NOT NULL | Clave primaria |
| `fecha_ingreso` | TIMESTAMP | NULL | Fecha y hora de ingreso del lote |

**Restricciones:**
- PRIMARY KEY: `id`

**Relaciones:**
- **1:N** con `Sublote` - Un lote puede contener múltiples sublotes
- **1:1** con `Compra` - Un lote corresponde a una compra

---

### 4. **sublote**

**Descripción:** Subdivisión de un lote con información específica de vencimiento, costos y cantidades.

**Columnas:**

| Columna | Tipo | Nullable | Descripción |
|---------|------|----------|-------------|
| `id` | INTEGER | NOT NULL | Clave primaria |
| `lote_id` | INTEGER | NULL | FK a `lote.id` |
| `producto_id` | INTEGER | NULL | FK a `producto.id` |
| `detalle_compra_id` | INTEGER | NULL | FK a `detalle_compra.id` |
| `fecha_vencimiento` | DATE | NULL | Fecha de vencimiento del sublote |
| `fecha_produccion` | DATE | NULL | Fecha de producción |
| `codigo_sublote` | VARCHAR | NULL | Código único del sublote |
| `cantidad_inicial` | DOUBLE | NULL | Cantidad al momento de ingreso |
| `cantidad_actual` | DOUBLE | NULL | Cantidad disponible actual |
| `costo_unitario` | DOUBLE | NULL | Costo por unidad del producto |
| `estado` | VARCHAR | NULL | Estado: DISPONIBLE, POCO_CANTIDAD, AGOTADO, RETIRADO |

**Restricciones:**
- PRIMARY KEY: `id`
- FOREIGN KEY: `lote_id` REFERENCES `lote(id)`
- FOREIGN KEY: `producto_id` REFERENCES `producto(id)`
- FOREIGN KEY: `detalle_compra_id` REFERENCES `detalle_compra(id)`
- CHECK: `estado` IN ('DISPONIBLE', 'POCO_CANTIDAD', 'AGOTADO', 'RETIRADO')

**Relaciones:**
- **N:1** con `Lote` - Muchos sublotes pertenecen a un lote
- **N:1** con `Producto` - Muchos sublotes del mismo producto
- **1:1** con `DetalleCompra` - Un sublote corresponde a un detalle de compra

---

### 5. **proveedor**

**Descripción:** Información de proveedores de productos.

**Columnas:**

| Columna | Tipo | Nullable | Descripción |
|---------|------|----------|-------------|
| `id` | INTEGER | NOT NULL | Clave primaria |
| `nombre` | VARCHAR | NULL | Nombre del proveedor |
| `correo` | VARCHAR | NULL | Correo electrónico |
| `telefono` | VARCHAR | NULL | Número de teléfono |
| `nit` | VARCHAR | NULL | Número de identificación tributaria |
| `ubicacion_id` | INTEGER | NULL | FK a `ubicacion.id` |

**Restricciones:**
- PRIMARY KEY: `id`
- FOREIGN KEY: `ubicacion_id` REFERENCES `ubicacion(id)`

**Relaciones:**
- **N:1** con `Ubicacion` - Un proveedor tiene una ubicación
- **1:N** con `Compra` - Un proveedor puede tener múltiples compras

---

## Enums Utilizados

### EstadoPedidoSugerido
```java
PENDIENTE   // Pedido generado, esperando revisión
APROBADO    // Pedido aprobado para ejecución
RECHAZADO   // Pedido rechazado con observación
EJECUTADO   // Pedido ejecutado (compra realizada)
```

### EstadoSublote
```java
DISPONIBLE      // Sublote con cantidad suficiente
POCO_CANTIDAD   // Sublote con poca cantidad
AGOTADO         // Sublote sin stock
RETIRADO        // Sublote retirado del inventario
```

---

## Flujo de Datos - Módulo KPI

### 1. Generación de Pedidos Sugeridos

```
Producto (stock_actual, punto_reorden, eoq)
    ↓
KPI Cálculo (stock actual vs ROP)
    ↓
SI stock_actual < punto_reorden ENTONCES
    ↓
Crear PedidoSugerido:
    - producto_id
    - cantidad_sugerida = eoq
    - fecha_sugerida = HOY
    - estado = PENDIENTE
    - stock_actual_momento = stock_actual
    - rop_momento = punto_reorden
```

### 2. Relación con Inventario

```
Compra
  ↓
Lote (fecha_ingreso)
  ↓
Sublote (cantidad_inicial, producto_id)
  ↓
Producto.stock_actual += Sublote.cantidad_inicial
  ↓
Verificar ROP → Generar PedidoSugerido si es necesario
```

---

## Índices Recomendados

Para optimizar consultas frecuentes:

```sql
-- Búsquedas por estado de pedido
CREATE INDEX idx_pedido_estado ON pedido_sugerido(estado);

-- Búsquedas de pedidos por producto
CREATE INDEX idx_pedido_producto ON pedido_sugerido(producto_id);

-- Búsquedas de sublotes por producto
CREATE INDEX idx_sublote_producto ON sublote(producto_id);

-- Búsquedas de sublotes por estado
CREATE INDEX idx_sublote_estado ON sublote(estado);

-- Clasificación ABC
CREATE INDEX idx_producto_abc ON producto(clasificacion_abc);
```

---

## Consultas Importantes

### Productos que necesitan pedido

```sql
SELECT p.id, p.nombre, 
       SUM(s.cantidad_actual) as stock_actual,
       p.punto_reorden,
       p.eoq
FROM producto p
LEFT JOIN sublote s ON p.id = s.producto_id
WHERE s.estado = 'DISPONIBLE'
GROUP BY p.id
HAVING SUM(s.cantidad_actual) < p.punto_reorden;
```

### Pedidos pendientes de aprobación

```sql
SELECT ps.id, p.nombre, ps.cantidad_sugerida, 
       ps.fecha_sugerida, ps.stock_actual_momento
FROM pedido_sugerido ps
JOIN producto p ON ps.producto_id = p.id
WHERE ps.estado = 'PENDIENTE'
ORDER BY ps.fecha_sugerida DESC;
```

### Stock actual por producto

```sql
SELECT p.id, p.nombre, 
       SUM(s.cantidad_actual) as stock_total,
       COUNT(s.id) as num_sublotes
FROM producto p
LEFT JOIN sublote s ON p.id = s.producto_id
WHERE s.estado IN ('DISPONIBLE', 'POCO_CANTIDAD')
GROUP BY p.id
ORDER BY stock_total ASC;
```

---

## Notas de Implementación

- **Paginación:** Implementada con `page=0` y `size=10` por defecto en lotes, sublotes y movimientos
- **Ordenamiento:** Todos los listados ordenados por fecha DESC (más recientes primero)
- **Gestión de transacciones:** JPA maneja CASCADE en relaciones OneToMany
- **Liquibase:** Removido del proyecto - gestión manual de esquema
- **CORS:** Configurado para frontend en producción (https://aquadebelenfront.prod.dtt.tja.ucb.edu.bo)
