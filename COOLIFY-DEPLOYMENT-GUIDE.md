# 🚀 Guía de Deployment en Coolify - Aqua de Belén

## 📋 Configuración de Coolify

### **1. General**
- **Name:** `Aqua_de_belen`
- **Build Pack:** `Dockerfile`
- **Description:** Sistema de gestión de inventario para perfumería

### **2. Git Source**
Conecta tu repositorio de GitHub:
- Repository: `Kevinho71/Aqua-De-Belen`
- Branch: `main`
- Auto Deploy: ✅ (opcional - despliega automáticamente al hacer push)

### **3. Domains**
- **Domain:** `https://aquadebelen.prod.ott.tja.ucb.edu.bo`
- ✅ Allow www & non-www

### **4. Build Configuration**
- **Base Directory:** `/` (raíz del proyecto)
- **Dockerfile Location:** `/Dockerfile`
- **Docker Build Stage Target:** (dejar vacío - usa multi-stage automáticamente)

### **5. Network**
- **Ports Exposes:** `8080`
- **Ports Mappings:** (dejar vacío - Coolify lo maneja automáticamente)
- **Network Aliases:** (dejar vacío)

---

## 🔐 Environment Variables (Configuración que necesitas llenar)

### **Production Environment Variables:**

#### 1️⃣ **SPRING_PROFILES_ACTIVE**
- **Key:** `SPRING_PROFILES_ACTIVE`
- **Value:** `prod`
- ⚠️ **Is Build Variable:** ❌ NO
- 🔒 **Is Literal:** ✅ SÍ

#### 2️⃣ **DATABASE_USERNAME**
- **Key:** `DATABASE_USERNAME`
- **Value:** `postgres.dagieipwpelsyxjkpaye`
- ⚠️ **Is Build Variable:** ❌ NO
- 🔒 **Is Literal:** ✅ SÍ

#### 3️⃣ **DATABASE_PASSWORD**
- **Key:** `DATABASE_PASSWORD`
- **Value:** `Tricampeon123`
- ⚠️ **Is Build Variable:** ❌ NO
- 🔒 **Is Literal:** ✅ SÍ (marca como literal para que no se interprete)

#### 4️⃣ **SERVER_PORT**
- **Key:** `SERVER_PORT`
- **Value:** `8080`
- ⚠️ **Is Build Variable:** ❌ NO
- 🔒 **Is Literal:** ✅ SÍ

#### 5️⃣ **DATABASE_URL**
- **Key:** `DATABASE_URL`
- **Value:** `jdbc:postgresql://aws-0-us-west-2.pooler.supabase.com:5432/postgres?sslmode=require`
- ⚠️ **Is Build Variable:** ❌ NO
- 🔒 **Is Literal:** ✅ SÍ

---

## 📝 Tabla Resumen de Variables de Entorno

| Variable | Valor | Build Variable | Multiline | Literal |
|----------|-------|----------------|-----------|---------|
| `SPRING_PROFILES_ACTIVE` | `prod` | ❌ | ❌ | ✅ |
| `DATABASE_USERNAME` | `postgres.dagieipwpelsyxjkpaye` | ❌ | ❌ | ✅ |
| `DATABASE_PASSWORD` | `Tricampeon123` | ❌ | ❌ | ✅ |
| `SERVER_PORT` | `8080` | ❌ | ❌ | ✅ |
| `DATABASE_URL` | `jdbc:postgresql://aws-0-us-west-2.pooler.supabase.com:5432/postgres?sslmode=require` | ❌ | ❌ | ✅ |

---

## 🎯 Pasos para Configurar en Coolify

### **Paso 1: Crear Aplicación**
1. En Coolify, click en **"+ New Resource"**
2. Selecciona **"Application"**
3. Elige **"Git Repository"**
4. Conecta con GitHub y selecciona el repositorio

### **Paso 2: Configuración General**
1. Ve a **"Configuration" > "General"**
2. Llena:
   - **Name:** `Aqua_de_belen`
   - **Build Pack:** Selecciona `Dockerfile`

### **Paso 3: Configurar Dominio**
1. Ve a **"Configuration" > "Domains"**
2. Añade: `https://aquadebelen.prod.ott.tja.ucb.edu.bo`
3. Marca ✅ **"Allow www & non-www"**
4. Click en **"Set Direction"**

### **Paso 4: Configurar Variables de Entorno**
1. Ve a **"Configuration" > "Environment Variables"**
2. Click en **"+ Add"** para cada variable
3. Copia exactamente como está en la tabla de arriba
4. **MUY IMPORTANTE:** Marca **"Is Literal"** en TODAS las variables

### **Paso 5: Configurar Red**
1. Ve a **"Configuration" > "Network"**
2. En **"Ports Exposes"** pon: `8080`

### **Paso 6: Deploy**
1. Click en el botón **"Redeploy"** o **"Deploy"**
2. Espera a que termine el build (puede tardar 3-5 minutos la primera vez)
3. Verifica los logs en la pestaña **"Logs"**

---

## ✅ Verificación Post-Deploy

Una vez desplegado, verifica que todo funciona:

### **1. Health Check**
```bash
curl https://aquadebelen.prod.ott.tja.ucb.edu.bo/actuator/health
```
Deberías ver: `{"status":"UP"}`

### **2. API Swagger**
Abre en el navegador:
```
https://aquadebelen.prod.ott.tja.ucb.edu.bo/swagger-ui.html
```

### **3. Frontend**
```
https://aquadebelen.prod.ott.tja.ucb.edu.bo/
```

---

## 🐛 Troubleshooting

### **Error: "Application failed to start"**
✅ **Solución:** Revisa los logs en Coolify > Logs
- Verifica que las variables de entorno estén correctas
- Asegúrate de que DATABASE_URL sea accesible desde Coolify

### **Error: "Port 8080 already in use"**
✅ **Solución:** Coolify maneja los puertos automáticamente, no necesitas cambiar nada

### **Error: "Database connection failed"**
✅ **Solución:**
1. Verifica que Supabase permita conexiones desde la IP de Coolify
2. Confirma que `sslmode=require` esté en DATABASE_URL
3. Verifica usuario y contraseña

### **La app se construye pero no responde**
✅ **Solución:**
1. Verifica que el puerto expuesto sea `8080`
2. Revisa los logs de la aplicación
3. Asegúrate de que SPRING_PROFILES_ACTIVE sea `prod`

---

## 🔄 Actualizar la Aplicación

### **Opción 1: Auto-deploy (Recomendado)**
1. Haz commit y push a `main` en GitHub
2. Coolify desplegará automáticamente

### **Opción 2: Manual**
1. En Coolify, click en **"Redeploy"**
2. Espera a que termine

---

## 📊 Monitoreo

### **Ver Logs en Tiempo Real**
1. Ve a **"Logs"** en Coolify
2. Selecciona el tipo de log:
   - **Build logs:** Ver proceso de construcción
   - **Application logs:** Ver logs de la aplicación Java

### **Métricas de Salud**
Endpoint: `https://aquadebelen.prod.ott.tja.ucb.edu.bo/actuator/health`

---

## 🎉 ¡Listo!

Tu aplicación debería estar corriendo en:
**https://aquadebelen.prod.ott.tja.ucb.edu.bo**

Si tienes problemas, revisa primero los logs en Coolify o contacta al equipo de DevOps.
