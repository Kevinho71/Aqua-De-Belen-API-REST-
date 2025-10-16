# ==========================================
# ROLLBACK INVENTORY MODULE
# ==========================================
# Este archivo contiene comandos específicos de rollback
# para el módulo de inventario agregado en 02-inventory-module.yaml

## ROLLBACK COMPLETO DEL MÓDULO DE INVENTARIO

### Para hacer rollback de TODO el módulo de inventario:
```bash
# Rollback de los 12 changesets del módulo inventario
mvn liquibase:rollback -Dliquibase.rollbackCount=12

# O rollback por fecha (si lo aplicaste hoy)
mvn liquibase:rollback -Dliquibase.rollbackDate=2025-10-15
```

## ROLLBACK GRANULAR POR FUNCIONALIDAD

### 1. Rollback solo de comentarios y documentación:
```bash
mvn liquibase:rollback -Dliquibase.rollbackCount=1
```

### 2. Rollback de constraints de validación:
```bash
mvn liquibase:rollback -Dliquibase.rollbackCount=2
```

### 3. Rollback de índices de performance:
```bash
mvn liquibase:rollback -Dliquibase.rollbackCount=3
```

### 4. Rollback de constraints únicos:
```bash
mvn liquibase:rollback -Dliquibase.rollbackCount=4
```

### 5. Rollback de foreign keys:
```bash
mvn liquibase:rollback -Dliquibase.rollbackCount=5
```

### 6. Rollback hasta antes de modificaciones de sublote:
```bash
mvn liquibase:rollback -Dliquibase.rollbackCount=7
```

### 7. Rollback completo (volver al baseline):
```bash
mvn liquibase:rollback -Dliquibase.rollbackCount=12
```

## ROLLBACK DE EMERGENCIA

### Si algo sale mal, puedes usar estos comandos:

#### Rollback con SQL preview (no ejecuta, solo muestra):
```bash
mvn liquibase:rollbackSQL -Dliquibase.rollbackCount=12
```

#### Rollback forzado (peligroso - úsalo solo en desarrollo):
```bash
mvn liquibase:rollback -Dliquibase.rollbackCount=12 -Dliquibase.force=true
```

## VERIFICACIÓN POST-ROLLBACK

### Después de hacer rollback, verifica el estado:
```bash
# Ver historial de changesets aplicados
mvn liquibase:history

# Ver estado actual
mvn liquibase:status

# Validar que la BD esté consistente
mvn liquibase:validate
```

## ROLLBACK SELECTIVO DE TABLAS

### Si solo necesitas eliminar tablas específicas:

#### Eliminar solo tabla movimiento:
```sql
-- Ejecutar manualmente en la BD si es necesario
DROP TABLE IF EXISTS movimiento CASCADE;
DELETE FROM databasechangelog WHERE id = 'create-movimiento-table-20251015-005';
```

#### Eliminar solo tablas de compras:
```sql
-- Ejecutar manualmente en la BD si es necesario
DROP TABLE IF EXISTS detalle_compra CASCADE;
DROP TABLE IF EXISTS compra CASCADE;
DELETE FROM databasechangelog WHERE id IN (
  'create-compra-table-20251015-002',
  'create-detalle-compra-table-20251015-003'
);
```

## NOTAS IMPORTANTES

1. **SIEMPRE haz backup** antes de aplicar o hacer rollback de cambios importantes
2. **En producción**: Coordina con el equipo antes de hacer rollback
3. **Verifica dependencias**: Algunas tablas pueden tener datos relacionados
4. **Rollback en orden**: Los changesets se revierten en orden inverso automáticamente

## COMANDOS DE VERIFICACIÓN RÁPIDA

```bash
# Ver qué changesets se aplicarían en rollback
mvn liquibase:rollbackSQL -Dliquibase.rollbackCount=5

# Ver diferencias con la BD actual
mvn liquibase:diff

# Generar changelog basado en BD actual
mvn liquibase:generateChangeLog
```