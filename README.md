# San Miguel Backend

Este proyecto corresponde al **backend** de PaginaSanMiguel, desarrollado con **Spring Boot**.

## 🚀 Ejecución en entorno local

### Requisitos previos
- Java 17 o superior
- Maven (incluido en el proyecto como `mvnw`)
- (Opcional) PostgreSQL para producción

### Configuración de variables de entorno

1. **Opción 1: Variables de entorno del sistema**
   - Configura las variables de entorno según tu sistema operativo
   - Consulta `.env.example` en la carpeta `demo` para ver todas las variables disponibles

2. **Opción 2: Usar valores por defecto**
   - El proyecto incluye valores por defecto en `application.properties`
   - Para desarrollo local, puedes ejecutar sin configurar variables adicionales

3. **Para producción:**
   - Copia `.env.example` a `.env` (o configura las variables de tu sistema)
   - Configura `SPRING_PROFILES_ACTIVE=prod`
   - Configura la conexión a PostgreSQL
   - **IMPORTANTE**: Cambia el `JWT_SECRET` por una clave segura y única

### Ejecutar el backend

Accede a la carpeta **demo** y ejecuta el siguiente comando:

**Windows (CMD/PowerShell):**
```cmd
cd demo
.\mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
cd demo
./mvnw spring-boot:run
```

El servidor se iniciará en `http://localhost:8080` (o el puerto configurado en `SERVER_PORT`).

### Variables de entorno principales

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `SPRING_PROFILES_ACTIVE` | Perfil activo (dev/prod) | dev |
| `SERVER_PORT` | Puerto del servidor | 8080 |
| `SPRING_DATASOURCE_URL` | URL de la base de datos | jdbc:h2:mem:testdb... |
| `SPRING_DATASOURCE_USERNAME` | Usuario de la base de datos | SanMiguel |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de la base de datos | (ver .env.example) |
| `JWT_SECRET` | Clave secreta para JWT | (ver .env.example) |
| `APP_UPLOAD_DIR` | Directorio para subir archivos | uploads |
