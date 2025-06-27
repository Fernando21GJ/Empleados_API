
# usuarios-api

API REST para gestión de usuarios con Spring Boot 3.5.0 y PostgreSQL

## 🚀 Cómo levantarlo

1. Clona el repositorio.
2. Construye el proyecto:
   ```bash
   ./mvnw clean package
   ```
3. Levanta con Docker Compose:
   ```bash
   docker-compose up --build
   ```
4. La API estará disponible en `http://localhost:8080/api/usuarios`

## 🔗 Endpoints

- POST /api/usuarios  
- GET /api/usuarios  
- GET /api/usuarios/{id}  
- PUT /api/usuarios/{id}  
- DELETE /api/usuarios/{id}  

5. Accede a la documentación interactiva (Swagger UI): `http://localhost:8080/swagger-ui.html`

