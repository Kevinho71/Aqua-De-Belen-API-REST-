# 📚 DOCUMENTACIÓN AUTOMÁTICA DE LA API - AQUA DE BELÉN

## 🚀 ¿Qué se agregó?

Se integró **SpringDoc OpenAPI 3** (anteriormente Swagger) para generar documentación automática e interactiva de todos los endpoints de la API REST.

---

## 📋 CÓMO ACCEDER A LA DOCUMENTACIÓN

### **1. Iniciar la aplicación:**
```bash
mvn spring-boot:run
```

### **2. Abrir Swagger UI (Interfaz Interactiva):**
```
http://localhost:8080/swagger-ui.html
```

### **3. Obtener el JSON de OpenAPI:**
```
http://localhost:8080/api-docs
```

---

## 🎯 ¿QUÉ PUEDES HACER CON SWAGGER UI?

### ✅ **Ver todos los endpoints agrupados por categorías:**
- Productos
- Clientes
- Compras
- Ventas
- Lotes
- Sublotes
- Movimientos

### ✅ **Ver detalles de cada endpoint:**
- **Método HTTP:** GET, POST, PUT, DELETE
- **Ruta completa:** `/api/v1/productos`
- **Parámetros requeridos:** Query params, Path params, Body
- **Estructura del Request Body:** Ejemplos con tipos de datos
- **Estructura de la Response:** Qué datos devuelve
- **Códigos de respuesta:** 200 OK, 400 Bad Request, 404 Not Found, etc.

### ✅ **PROBAR ENDPOINTS DIRECTAMENTE DESDE EL NAVEGADOR:**
1. Click en cualquier endpoint
2. Click en "Try it out"
3. Completar los parámetros
4. Click en "Execute"
5. Ver la respuesta en tiempo real

**No necesitas Postman** si usas Swagger UI.

---

## 📤 COMPARTIR LA DOCUMENTACIÓN

### **Opción 1: Compartir el JSON de OpenAPI**

Descarga el JSON:
```
http://localhost:8080/api-docs
```

Compártelo con:
- Frontend developers
- Otros backend developers
- Herramientas de testing
- **IA (ChatGPT, Claude, Copilot)** → Pega el JSON completo

### **Opción 2: Exportar a Postman**

1. Abrir Postman
2. `File` → `Import`
3. Pegar la URL: `http://localhost:8080/api-docs`
4. Postman crea automáticamente una colección con TODOS tus endpoints

### **Opción 3: Generar documentación estática**

Puedes exportar a HTML/PDF usando herramientas como:
- `swagger-codegen`
- `redoc-cli`
- Plugins de Maven

---

## 🤖 CÓMO USAR CON IA

### **Ejemplo: Pedir ayuda a ChatGPT/Claude/Copilot**

```
Prompt para la IA:

Tengo esta API REST documentada con OpenAPI 3. Aquí está el JSON completo:

[PEGA EL CONTENIDO DE http://localhost:8080/api-docs]

Por favor, ayúdame a:
1. Crear un cliente React que consuma el endpoint POST /api/v1/compras
2. Generar validaciones para el formulario de productos
3. Crear tests unitarios para los endpoints de búsqueda
```

La IA entenderá perfectamente:
- ✅ Qué endpoints existen
- ✅ Qué parámetros requieren
- ✅ Qué estructura de datos esperas
- ✅ Qué respuestas devuelves

---

## 🛠️ PERSONALIZAR LA DOCUMENTACIÓN

### **Agregar descripciones a tus controllers:**

```java
@Tag(name = "Productos", description = "Gestión de productos del inventario")
@RestController
public class ProductoController {
    
    @Operation(
        summary = "Crear producto",
        description = "Registra un nuevo producto en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Producto creado")
    @PostMapping("/productos")
    public ResponseEntity<ProductoViewModel> registrar(@RequestBody ProductoDTORequest req) {
        // ...
    }
}
```

### **Documentar parámetros:**

```java
@GetMapping("/productos/buscar")
public ResponseEntity<List<ProductoViewModel>> buscar(
    @Parameter(description = "Nombre del producto a buscar")
    @RequestParam String nombre) {
    // ...
}
```

---

## 📊 BENEFICIOS

### **Para el desarrollo:**
- ✅ No necesitas escribir documentación manual
- ✅ La documentación siempre está actualizada (se genera del código)
- ✅ Puedes probar endpoints sin Postman

### **Para el equipo:**
- ✅ Frontend sabe exactamente qué endpoints llamar
- ✅ Nuevos developers entienden la API rápidamente
- ✅ Testing más fácil

### **Para la IA:**
- ✅ Puedes compartir tu API completa en un solo JSON
- ✅ La IA genera código frontend/tests automáticamente
- ✅ No necesitas explicar manualmente cada endpoint

---

## 🎨 EJEMPLO DE USO CON IA

**Tú:**
```
Aquí está mi API: [pega JSON de /api-docs]

Necesito un componente React que:
1. Muestre una tabla con todos los productos
2. Tenga un buscador por nombre
3. Permita filtrar por tipo de producto
```

**La IA generará:**
- ✅ Componente React completo
- ✅ Llamadas axios correctas a `/api/v1/productos/buscar?nombre=...`
- ✅ Manejo de estados y loading
- ✅ Tipos TypeScript basados en tu Response

---

## 📝 ARCHIVOS MODIFICADOS

1. `pom.xml` → Agregada dependencia springdoc-openapi
2. `application.properties` → Configuración de rutas
3. `OpenAPIConfig.java` → Configuración general de la API
4. `ProductoController.java` → Ejemplo con anotaciones @Operation

---

## 🔗 RECURSOS

- **Documentación oficial:** https://springdoc.org/
- **OpenAPI 3 Spec:** https://swagger.io/specification/
- **Swagger UI:** Tu aplicación en `/swagger-ui.html`

---

## ⚡ PRÓXIMOS PASOS

1. ✅ **Descarga el JSON** de `/api-docs`
2. ✅ **Pásalo a la IA** cuando necesites ayuda con frontend
3. ✅ **Importa a Postman** para testing manual
4. ✅ **Agrega anotaciones** @Operation a otros controllers (opcional)

**Con esto, cualquier IA puede entender tu API completa en segundos.** 🚀
