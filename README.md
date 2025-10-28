Instrucciones de la instalacion:
1. abrir la terminal en la carpeta y poner el siguiente 
  -mvn clean package -DskipTests
  -docker compose up --build
2. probar de la coleccion adjunta para probar los endpoints.

Prueba Técnica Backend - Linktic

Este proyecto consiste en una arquitectura de **microservicios** que simula la gestión de productos e inventario, implementando comunicación entre servicios mediante **REST**.  
El objetivo es realizar operaciones de consulta y compra de productos actualizando el inventario de forma sincronizada.

---

Estructura del Proyecto

El sistema está compuesto por **dos microservicios principales**:

### 1. `productos-service`
Gestiona la información de los productos (nombre, precio, etc.).

Ubicación: `./productos`  
Puerto: `8081`

Endpoints principales:
- **GET** `/productos/{id}` → Obtiene la información de un producto específico.

### 2. `inventario-service`
Administra el stock de productos y permite realizar compras, consultando al servicio de productos para calcular precios.

Ubicación: `./inventario`  
Puerto: `8082`

Endpoints principales:
- **GET** `/inventario/{id}` → Consulta el inventario de un producto.  
- **PUT** `/inventario/{id}` → Actualiza la cantidad disponible.  
- **POST** `/inventario/compras` → Realiza una compra (resta del inventario y obtiene datos del producto).

---

Tecnologías Utilizadas

- Java 17
- Spring Boot 3.3.5
- Maven
- JPA / Hibernate
- H2 Database (en memoria)
- Docker & Docker Compose
- JUnit 5 / Mockito (para pruebas unitarias)

Base de datos
Permite ejecutar los servicios rápidamente sin depender de configuraciones externas ni credenciales, ideal para pruebas técnicas y entornos Docker efímeros.

IA
Se uso ChatGPT para la correccion de errores para agilizar el proceso.

