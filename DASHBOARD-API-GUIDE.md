# API Dashboard - Documentación de Endpoints

## Endpoints Disponibles

### 1. **GET** `/api/v1/dashboard/estadisticas`

Obtiene todas las estadísticas generales del dashboard en una sola consulta optimizada.

**Response:**
```json
{
  "totalVentas": 584290.0,
  "numeroVentas": 42,
  "totalCompras": 450000.0,
  "numeroCompras": 15,
  "productosProximosVencer": 2,
  "productosBajoStock": 6,
  "clientesRegistrados": 125
}
```

**Descripción de campos:**
- `totalVentas`: Monto total de ventas (excluye anuladas)
- `numeroVentas`: Cantidad de transacciones de venta
- `totalCompras`: Monto total de compras realizadas
- `numeroCompras`: Cantidad de transacciones de compra
- `productosProximosVencer`: Productos que vencen en 30 días
- `productosBajoStock`: Productos con stock < punto de reorden
- `clientesRegistrados`: Total de clientes en el sistema

---

### 2. **GET** `/api/v1/dashboard/top-productos-stock`

Obtiene los 5 productos con mayor stock disponible.

**Response:**
```json
[
  {
    "productoId": 1,
    "nombreProducto": "Kit de Brochas Professional 11pz",
    "stockTotal": 195.0
  },
  {
    "productoId": 2,
    "nombreProducto": "Set Pinceles Cololr Full",
    "stockTotal": 104.0
  },
  {
    "productoId": 3,
    "nombreProducto": "Pestañ Exx",
    "stockTotal": 100.0
  }
]
```

**Uso:** Gráfico de barras "Top 5 Productos con Mayor Stock"

---

### 3. **GET** `/api/v1/dashboard/ventas-recientes`

Obtiene las últimas 5 ventas realizadas.

**Response:**
```json
[
  {
    "ventaId": 101,
    "nombreCliente": "Juan Pérez García",
    "fechaVenta": "2025-12-08T17:49:14",
    "totalVenta": 32500.0
  },
  {
    "ventaId": 98,
    "nombreCliente": "Carlos Mendoza Silva",
    "fechaVenta": "2025-11-26T10:05:46",
    "totalVenta": 8478.75
  }
]
```

**Uso:** Tabla "Ventas Recientes" en el dashboard

---

### 4. **GET** `/api/v1/dashboard/distribucion-stock`

Obtiene la distribución porcentual de stock por producto (máximo 6 productos).

**Response:**
```json
[
  {
    "nombreProducto": "Kit de Brochas Professional 11pz",
    "porcentaje": 27.34,
    "stockTotal": 195.0
  },
  {
    "nombreProducto": "Loción Body Care",
    "porcentaje": 14.12,
    "stockTotal": 100.8
  },
  {
    "nombreProducto": "Jean Paul Gault...",
    "porcentaje": 13.45,
    "stockTotal": 96.0
  }
]
```

**Uso:** Gráfico de pie "Distribución de Stock"

---

### 5. **GET** `/api/v1/dashboard/sublotes-proximos-vencer`

Obtiene los sublotes que vencen en los próximos 30 días.

**Response:**
```json
[
  {
    "nombreProducto": "Dior",
    "cantidad": 50.0,
    "fechaVencimiento": "2025-12-10"
  },
  {
    "nombreProducto": "Kit de Brochas Professional 11pz",
    "cantidad": 40.0,
    "fechaVencimiento": "2025-12-31"
  }
]
```

**Uso:** Tabla "Sublotes Próximos a Vencer"

---

## Consultas SQL Optimizadas

Todas las consultas están optimizadas para rendimiento:

### Características de Optimización:

1. **Consultas nativas SQL** - Mejor rendimiento que JPQL
2. **COALESCE** - Maneja valores NULL correctamente
3. **Índices sugeridos:**
   ```sql
   CREATE INDEX idx_venta_estado ON venta(estado);
   CREATE INDEX idx_venta_fecha ON venta(fecha DESC);
   CREATE INDEX idx_sublote_estado ON sublote(estado);
   CREATE INDEX idx_sublote_vencimiento ON sublote(fecha_vencimiento);
   CREATE INDEX idx_producto_descontinuado ON producto(descontinuado);
   ```
4. **WHERE con estado** - Filtra datos irrelevantes
5. **LIMIT** - Restringe resultados para mejor rendimiento
6. **Agregaciones eficientes** - SUM, COUNT en una sola consulta

---

## Mapeo con la Imagen del Dashboard

### Tarjetas Superiores (KPIs)
- **Productos Bajo Stock (6)** → `estadisticas.productosBajoStock`
- **Total Compras (584290.00 Bs)** → `estadisticas.totalCompras` + `estadisticas.numeroCompras`
- **Productos por Vencer (2)** → `estadisticas.productosProximosVencer`

### Gráficos
- **Top 5 Productos con Mayor Stock** → `/top-productos-stock`
- **Distribución de Stock** (pie chart) → `/distribucion-stock`

### Tablas
- **Ventas Recientes** → `/ventas-recientes`
- **Sublotes Próximos a Vencer** → `/sublotes-proximos-vencer`

### Alerta
- **"Hay 6 productos con menos de 10 unidades en stock"** → `estadisticas.productosBajoStock`

---

## Ejemplo de Uso en Frontend

```javascript
// Obtener todas las estadísticas
const stats = await fetch('/api/v1/dashboard/estadisticas').then(r => r.json());

// Actualizar tarjetas
document.getElementById('productosBajoStock').textContent = stats.productosBajoStock;
document.getElementById('totalCompras').textContent = `${stats.totalCompras} Bs`;
document.getElementById('productosVencer').textContent = stats.productosProximosVencer;

// Obtener datos para gráficos
const topProductos = await fetch('/api/v1/dashboard/top-productos-stock').then(r => r.json());
const distribucion = await fetch('/api/v1/dashboard/distribucion-stock').then(r => r.json());

// Renderizar gráfico de barras
renderBarChart(topProductos);

// Renderizar gráfico de pie
renderPieChart(distribucion);

// Obtener tablas
const ventas = await fetch('/api/v1/dashboard/ventas-recientes').then(r => r.json());
const sublotes = await fetch('/api/v1/dashboard/sublotes-proximos-vencer').then(r => r.json());

renderVentasTable(ventas);
renderSublotesTable(sublotes);
```

---

## Notas Importantes

1. **Todas las consultas excluyen productos descontinuados** (excepto donde se especifique)
2. **Las ventas anuladas no se cuentan** en estadísticas
3. **Solo sublotes DISPONIBLE y POCO_CANTIDAD** se consideran en stock
4. **Fechas de vencimiento** se calculan desde HOY + 30 días
5. **Transacciones de solo lectura** (@Transactional(readOnly = true))
6. **CORS habilitado** para frontend en producción

---

## Performance

Tiempos de respuesta estimados (base de datos con 1000+ registros):
- `/estadisticas`: ~50-100ms (1 query con subconsultas)
- `/top-productos-stock`: ~20-40ms
- `/ventas-recientes`: ~10-20ms
- `/distribucion-stock`: ~30-50ms
- `/sublotes-proximos-vencer`: ~15-30ms

**Total para cargar dashboard completo: ~150-250ms**
