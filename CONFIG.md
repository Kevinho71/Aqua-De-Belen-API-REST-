# 🔧 Configuración del Proyecto - Aqua de Belen

## 📋 Configuración Inicial

### 1. **Configuración de Base de Datos (application.properties)**

1. Copiar el template:
   ```bash
   cp src/main/resources/application.properties.template src/main/resources/application.properties
   ```

2. Editar `application.properties` y reemplazar:
   ```properties
   # Reemplazar estos valores con tus credenciales reales
   spring.datasource.url=jdbc:postgresql://YOUR_HOST:5432/YOUR_DATABASE?user=YOUR_USER&password=YOUR_PASSWORD
   spring.datasource.username=YOUR_USERNAME  
   spring.datasource.password=YOUR_PASSWORD
   ```

### 2. **Configuración de Liquibase (liquibase.properties)**

1. Copiar el template:
   ```bash
   cp src/main/resources/db/liquibase.properties.template src/main/resources/db/liquibase.properties
   ```

2. Editar `liquibase.properties` con las mismas credenciales de BD

## 🚨 **IMPORTANTE - Seguridad**

### ❌ **NO hacer commit de estos archivos:**
- `application.properties`
- `liquibase.properties`
- Archivos con credenciales reales

### ✅ **SÍ hacer commit de:**
- `application.properties.template`
- `liquibase.properties.template`
- Código fuente sin credenciales

## 🔄 **Comandos de Liquibase**

### Comandos básicos:
```bash
# Ver estado de la BD
mvn liquibase:status

# Aplicar cambios pendientes  
mvn liquibase:update

# Ver historial de cambios
mvn liquibase:history

# Validar changesets
mvn liquibase:validate

# Generar SQL sin ejecutar (para revisar)
mvn liquibase:updateSQL
```

### Rollback:
```bash
# Rollback de 1 changeset
mvn liquibase:rollback -Dliquibase.rollbackCount=1

# Rollback a una fecha específica
mvn liquibase:rollback -Dliquibase.rollbackDate=2025-10-14
```

## 📁 **Estructura de Liquibase**

```
src/main/resources/db/
├── liquibase.properties.template      # Template de configuración
├── liquibase.properties              # Configuración real (NO commitear)
└── changelog/
    ├── db.changelog-master.yaml      # Master changelog (incluye todos)
    ├── 01-baseline.yaml             # Estado inicial de BD  
    ├── 02-add-inventory.yaml        # Próximos cambios
    └── 03-modify-constraints.yaml   # Siguientes cambios
```

## 🏗️ **Agregar nuevos cambios de BD**

### 1. Crear nuevo changelog:
```yaml
# 02-add-inventory-module.yaml
databaseChangeLog:
  - changeSet:
      id: create-inventory-table-20251014
      author: tu-nombre
      changes:
        - createTable:
            tableName: inventory
            columns:
              - column:
                  name: id
                  type: INTEGER
                  autoIncrement: true
                  constraints:
                    primaryKey: true
```

### 2. Registrar en master changelog:
```yaml
# db.changelog-master.yaml
databaseChangeLog:
  - include:
      file: changelog/01-baseline.yaml
      relativeToChangelogFile: true
  - include:
      file: changelog/02-add-inventory-module.yaml  # ← Agregar aquí
      relativeToChangelogFile: true
```

### 3. Aplicar cambios:
```bash
mvn liquibase:update
```

## 🌍 **Configuración por Entornos**

### Desarrollo:
```properties
# application-dev.properties
spring.jpa.show-sql=true
logging.level.org.hibernate.SQL=DEBUG
```

### Producción:
```properties  
# application-prod.properties
spring.jpa.show-sql=false
logging.level.root=WARN
```

## 🚀 **Iniciar el Proyecto**

1. Clonar repositorio
2. Configurar `application.properties` (ver arriba)
3. Configurar `liquibase.properties` (ver arriba)  
4. Ejecutar: `mvn spring-boot:run`

## 📞 **Soporte**

Si tienes problemas con la configuración, contacta al equipo de desarrollo.