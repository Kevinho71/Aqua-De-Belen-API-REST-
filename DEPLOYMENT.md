# Guía de Despliegue - Aqua de Belén API

## Despliegue en Coolify

### Configuración Inicial

1. **Repository URL**: `https://github.com/Kevinho71/Aqua-De-Belen-API-REST-`
2. **Branch**: `main`
3. **Build Pack**: `Dockerfile`
4. **Port**: `8080`
5. **Base Directory**: `/`

### Variables de Entorno Requeridas

En Coolify, después de crear la aplicación, configura estas variables de entorno:

```env
# Perfil de Spring Boot
SPRING_PROFILES_ACTIVE=prod

# Base de datos (ajusta según tu BD en Coolify)
DATABASE_URL=jdbc:postgresql://postgres:5432/aquadebelen
DATABASE_USERNAME=tu_usuario
DATABASE_PASSWORD=tu_contraseña

# Puerto (opcional, por defecto 8080)
PORT=8080
```

### Configuración de Base de Datos en Coolify

1. Ve a **Databases** en Coolify
2. Crea una nueva base de datos PostgreSQL
3. Anota los datos de conexión:
   - Host (generalmente el nombre del servicio de PostgreSQL)
   - Puerto (generalmente 5432)
   - Nombre de la base de datos
   - Usuario
   - Contraseña

4. Usa estos datos para construir la `DATABASE_URL`:
   ```
   jdbc:postgresql://[HOST]:[PORT]/[DATABASE_NAME]
   ```

### Health Check

Coolify puede verificar que tu aplicación esté funcionando:

- **Health Check Path**: `/actuator/health` (si tienes Spring Actuator)
- **O simplemente**: `/api/v1/productos` (cualquier endpoint público)

### Acceso a Swagger

Una vez desplegado, accede a la documentación de la API:
```
https://tu-dominio.coolify.app/swagger-ui/index.html
```

### Logs

Para ver los logs de la aplicación en Coolify:
1. Ve a tu aplicación
2. Click en **Logs**
3. Monitorea errores de conexión a BD o inicio de la aplicación

### Solución de Problemas

**Error de conexión a base de datos:**
- Verifica que las variables de entorno estén correctamente configuradas
- Asegúrate de que el servicio de PostgreSQL esté en la misma red que tu aplicación
- En Coolify, usa el nombre del servicio de PostgreSQL como host

**La aplicación no inicia:**
- Revisa los logs en Coolify
- Verifica que el puerto 8080 esté expuesto
- Asegúrate de que Spring Boot esté usando el perfil `prod`

**Liquibase falla:**
- Verifica que la base de datos exista
- Asegúrate de que el usuario tenga permisos para crear tablas
- Revisa que los archivos de changelog estén en `src/main/resources/db/changelog/`

## Comandos Útiles

### Build local del Docker
```bash
docker build -t aquadebelen-api .
docker run -p 8080:8080 aquadebelen-api
```

### Variables de entorno para pruebas locales
```bash
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DATABASE_URL=jdbc:postgresql://localhost:5432/aquadebelen \
  -e DATABASE_USERNAME=postgres \
  -e DATABASE_PASSWORD=postgres \
  aquadebelen-api
```
