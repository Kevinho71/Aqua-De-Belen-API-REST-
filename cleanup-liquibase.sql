-- Script para limpiar completamente Liquibase de la base de datos
-- Ejecuta esto en Supabase SQL Editor

-- 1. Liberar cualquier lock activo
UPDATE databasechangeloglock 
SET locked = FALSE, 
    lockgranted = NULL, 
    lockedby = NULL 
WHERE id = 1;

-- 2. Verificar que el lock se liberó
SELECT * FROM databasechangeloglock;

-- 3. (OPCIONAL) Eliminar completamente las tablas de Liquibase
-- Descomenta las siguientes líneas si quieres eliminarlas por completo
-- DROP TABLE IF EXISTS databasechangelog;
-- DROP TABLE IF EXISTS databasechangeloglock;

-- 4. Verificar que no hay locks activos
SELECT 
    CASE 
        WHEN EXISTS (SELECT 1 FROM databasechangeloglock WHERE locked = TRUE) 
        THEN 'HAY LOCKS ACTIVOS ❌' 
        ELSE 'NO HAY LOCKS ✅' 
    END AS estado_locks;
