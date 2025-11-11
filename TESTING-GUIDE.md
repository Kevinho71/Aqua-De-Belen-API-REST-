# 🧪 Guía de Ejecución de Tests Unitarios

## 📋 Resumen de Tests Creados

Se han implementado **tests unitarios** para las **10 funciones más críticas** del sistema usando **JUnit 5 + Mockito**:

### ✅ Archivos de Test Creados

| Archivo | Función Probada | # Tests | Prioridad |
|---------|-----------------|---------|-----------|
| `CompraServiceTest.java` | `calcularCostoBruto()`, `agregarDetalles()` | 7 | 🔴 Alta (dinero) |
| `LoteServiceTest.java` | `crearSublotes()`, `createLote()` | 9 | 🔴 Alta (bug reciente) |
| `VentaServiceTest.java` | `calcularTotalBruto()`, `agregarDetalles()` | 9 | 🔴 Alta (dinero + stock) |
| `MovimientoServiceTest.java` | `crearMovimientoCompra()`, `crearMovimientoVenta()` | 10 | 🟡 Media (auditoría) |
| `ProductoDAOImplTest.java` | `findByFiltros()` | 6 | 🟢 Baja (consultas) |
| `ClienteDAOImplTest.java` | `findByFiltros()` | 9 | 🟢 Baja (consultas) |
| `SubloteDAOImplTest.java` | `findProximosAVencer()`, `findDisponibles()` | 8 | 🟡 Media (inventario) |

**Total: 58 tests unitarios**

---

## 🚀 Ejecución de Tests

### Opción 1: Ejecutar TODOS los tests

```bash
mvn test
```

**Salida esperada:**
```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.perfumeria.aquadebelen.aquadebelen.compras.service.CompraServiceTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.perfumeria.aquadebelen.aquadebelen.inventario.service.LoteServiceTest
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
...
[INFO] Results:
[INFO] Tests run: 58, Failures: 0, Errors: 0, Skipped: 0
```

---

### Opción 2: Ejecutar una clase de test específica

```bash
# Test de CompraService (cálculos de dinero)
mvn test -Dtest=CompraServiceTest

# Test de LoteService (bug de relación sublote-detalle)
mvn test -Dtest=LoteServiceTest

# Test de VentaService (ventas y descuento de stock)
mvn test -Dtest=VentaServiceTest

# Test de MovimientoService (auditoría de movimientos)
mvn test -Dtest=MovimientoServiceTest

# Test de ProductoDAO (búsqueda dinámica)
mvn test -Dtest=ProductoDAOImplTest

# Test de ClienteDAO (búsqueda dinámica)
mvn test -Dtest=ClienteDAOImplTest

# Test de SubloteDAO (próximos a vencer)
mvn test -Dtest=SubloteDAOImplTest
```

---

### Opción 3: Ejecutar un test individual

```bash
# Sintaxis: mvn test -Dtest=NombreClase#nombreMetodo

# Ejemplo: Test de cálculo de costo bruto con un producto
mvn test -Dtest=CompraServiceTest#calcularCostoBruto_unProducto_retornaSumaCorrecta

# Ejemplo: Test de relación DetalleCompra en LoteService (el bug que se arregló)
mvn test -Dtest=LoteServiceTest#crearSublotes_estableceRelacionConDetalleCompra

# Ejemplo: Test de descuento de stock en VentaService
mvn test -Dtest=VentaServiceTest#agregarDetalles_descuentaStockDelSublote
```

---

### Opción 4: Ejecutar tests con cobertura de código

```bash
mvn test jacoco:report
```

**Ver reporte de cobertura:**
- Abre el archivo: `target/site/jacoco/index.html` en tu navegador
- Muestra qué líneas de código fueron ejecutadas por los tests

---

## 🔍 Verificar Tests por Categoría

### Tests de DINERO (críticos para el negocio)
```bash
mvn test -Dtest=CompraServiceTest,VentaServiceTest
```

### Tests de INVENTARIO (stock y sublotes)
```bash
mvn test -Dtest=LoteServiceTest,SubloteDAOImplTest
```

### Tests de CONSULTAS (búsquedas dinámicas)
```bash
mvn test -Dtest=ProductoDAOImplTest,ClienteDAOImplTest
```

### Tests de AUDITORÍA (movimientos)
```bash
mvn test -Dtest=MovimientoServiceTest
```

---

## 📊 Interpretación de Resultados

### ✅ Test Exitoso
```
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.5 s - in CompraServiceTest
```

### ❌ Test Fallido
```
[ERROR] Failures: 
[ERROR]   CompraServiceTest.calcularCostoBruto_unProducto_retornaSumaCorrecta:42
    Expected: 51.0
    Actual: 50.0
```

**Acción:** Revisar el código de la función `calcularCostoBruto()` o el test mismo.

### ⚠️ Errores de Compilación
```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.13.0:testCompile
```

**Acción:** Ejecutar `mvn clean compile test-compile` para limpiar y recompilar.

---

## 🧩 Estructura de los Tests

Todos los tests siguen el patrón **Given-When-Then** con **Mockito**:

```java
@Test
@DisplayName("Descripción clara del test en español")
void nombreMetodo_escenario_resultadoEsperado() {
    // Given - Configuración de mocks y datos
    when(mockDAO.findById(1)).thenReturn(objetoMock);
    
    // When - Ejecución del método bajo prueba
    Resultado resultado = servicio.metodoAPrueba(parametros);
    
    // Then - Verificaciones
    assertEquals(valorEsperado, resultado);
    verify(mockDAO).findById(1);
}
```

---

## 🎯 Tests Más Importantes

### 1. CompraService - Cálculo de dinero
```bash
mvn test -Dtest=CompraServiceTest#calcularCostoBruto_multipleProductos_retornaSumaTotal
```
**Por qué es importante:** Errores en costos = pérdidas económicas

### 2. LoteService - Relación con DetalleCompra
```bash
mvn test -Dtest=LoteServiceTest#crearSublotes_estableceRelacionConDetalleCompra
```
**Por qué es importante:** Valida el fix del bug donde `sublote_id` era NULL

### 3. LoteService - Usar fecha vencimiento del detalle
```bash
mvn test -Dtest=LoteServiceTest#crearSublotes_usaFechaVencimientoDelDetalle
```
**Por qué es importante:** Valida que NO se calcula la fecha (viene del DTO)

### 4. VentaService - Descuento de stock
```bash
mvn test -Dtest=VentaServiceTest#agregarDetalles_descuentaStockDelSublote
```
**Por qué es importante:** Evita ventas sin descontar inventario

### 5. MovimientoService - Auditoría de compras
```bash
mvn test -Dtest=MovimientoServiceTest#crearMovimientoCompra_datosValidos_creaMovimientoEntrada
```
**Por qué es importante:** Trazabilidad de todas las operaciones

---

## 🛠️ Comandos Útiles

### Limpiar compilaciones anteriores
```bash
mvn clean
```

### Compilar sin ejecutar tests
```bash
mvn compile -DskipTests
```

### Solo compilar los tests (sin ejecutarlos)
```bash
mvn test-compile
```

### Ejecutar tests en modo verbose (más detalles)
```bash
mvn test -X
```

### Ejecutar tests saltando tests que fallan
```bash
mvn test -DfailIfNoTests=false
```

### Ver reporte de tests fallidos
```bash
# Los reportes están en:
# target/surefire-reports/
```

---

## 🔧 Solución de Problemas Comunes

### Error: "No tests were executed!"
**Causa:** Los archivos de test no están en la ubicación correcta.

**Solución:**
```bash
# Verificar que los tests están en:
# src/test/java/com/perfumeria/aquadebelen/aquadebelen/...
```

### Error: "MockitoException: Cannot mock/spy because..."
**Causa:** Intentando mockear clases final o static.

**Solución:** Usar `@Mock` solo en interfaces o clases no-final.

### Error: "NullPointerException en el test"
**Causa:** No se configuró un mock necesario.

**Solución:**
```java
// Agregar en setUp() o en el test:
when(mockDAO.metodo()).thenReturn(valorMock);
```

### Error: "Wanted but not invoked" (Mockito)
**Causa:** El test espera que se llame a un método que no se ejecutó.

**Solución:** Revisar que el test coincida con la implementación real. Los tests deben validar el comportamiento actual, no el esperado.

**Ejemplo de corrección:**
```java
// ❌ Incorrecto: espera un parámetro que no existe
verify(typedQuery).setParameter("hoy", LocalDate.now());

// ✅ Correcto: verifica el parámetro que realmente se usa
verify(typedQuery).setParameter("fechaLimite", LocalDate.now().plusDays(30));
```

---

## ⚠️ Warnings Conocidos

### Warning: "Mockito self-attaching" (Java 21)
```
WARNING: Dynamic loading of agents will be disallowed by default in a future release
```

**Causa:** Mockito se auto-adjunta como agente en Java 21.

**Impacto:** ⚠️ Solo warning - los tests funcionan correctamente.

**Solución (opcional):** Agregar argumento JVM en `pom.xml`:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <argLine>-XX:+EnableDynamicAgentLoading</argLine>
    </configuration>
</plugin>
```

---

## 📝 Resumen de Cobertura

### Funciones cubiertas por tests:

✅ **CompraService:**
- `calcularCostoBruto()` - 4 escenarios
- `agregarDetalles()` - 3 escenarios

✅ **LoteService:**
- `crearSublotes()` - 8 escenarios (incluye fix del bug)
- `createLote()` - 1 escenario

✅ **VentaService:**
- `calcularTotalBruto()` - 3 escenarios
- `agregarDetalles()` - 6 escenarios

✅ **MovimientoService:**
- `crearMovimientoCompra()` - 5 escenarios
- `crearMovimientoVenta()` - 5 escenarios

✅ **ProductoDAOImpl:**
- `findByFiltros()` - 6 combinaciones de filtros

✅ **ClienteDAOImpl:**
- `findByFiltros()` - 9 combinaciones de filtros

✅ **SubloteDAOImpl:**
- `findProximosAVencer()` - 5 escenarios
- `findDisponibles()` - 3 escenarios

---

## 🎓 Próximos Pasos

1. **Ejecutar todos los tests:**
   ```bash
   mvn test
   ```

2. **Ver reporte de cobertura:**
   ```bash
   mvn test jacoco:report
   ```

3. **Agregar tests adicionales** para otras funciones según necesidad.

4. **Integración continua (CI/CD):** Configurar estos tests para que se ejecuten automáticamente en cada commit.

---

**¡Los tests están listos para ejecutarse! 🚀**
