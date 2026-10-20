<div align="center">

# 🛒 Supermarket System

### API REST para la gestión de un supermercado

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Base_de_datos-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Wrapper-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-Colecci%C3%B3n-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

**Categorías · Proveedores · Clientes · Productos · Ventas**

</div>

---

## 📑 Contenido

- [✨ Qué hace el proyecto](#-qué-hace-el-proyecto)
- [🧰 Tecnologías](#-tecnologías)
- [🗂️ Estructura del proyecto](#️-estructura-del-proyecto)
- [🗃️ Modelo de datos](#️-modelo-de-datos)
- [✅ Requisitos previos](#-requisitos-previos)
- [🚀 Instalación y ejecución](#-instalación-y-ejecución)
- [📮 Colección de Postman](#-colección-de-postman)
- [🔗 Endpoints](#-endpoints)
- [🧠 Reglas de negocio](#-reglas-de-negocio)
- [🚦 Códigos de respuesta](#-códigos-de-respuesta)
- [🛠️ Solución de problemas](#️-solución-de-problemas)

---

## ✨ Qué hace el proyecto

Es el backend de un supermercado. Permite:

- 📂 Administrar **categorías** y 🚚 **proveedores**.
- 🧑 Registrar **clientes** y buscarlos por documento.
- 📦 Controlar **productos** con precio, stock y código de barras (con búsqueda por nombre, por categoría y por stock bajo).
- 🧾 Registrar **ventas** con varios productos en una sola petición. El sistema descuenta el stock y calcula el total solo. También se pueden consultar por cliente o por rango de fechas.

---

## 🧰 Tecnologías

| Tecnología | Versión | Para qué se usa |
|---|---|---|
| ☕ Java | 21 | Lenguaje |
| 🍃 Spring Boot | 4.1.1 | Framework de la API |
| 🗄️ Spring Data JPA / Hibernate | — | Acceso a la base de datos |
| 🐘 PostgreSQL | 14 o superior | Base de datos |
| 🧪 Lombok | — | Menos código repetido (getters y setters) |
| 📦 Maven Wrapper | — | Compilar y ejecutar sin instalar Maven |
| 📮 Postman | — | Probar los endpoints |

---

## 🗂️ Estructura del proyecto

```text
Supermarket_system/
├── 📁 Database/
│   └── db_supermarket.sql              ← script para crear las tablas
├── 📁 Colección de Postman/
│   └── Supermarket_API.postman_collection.json
├── 📁 backend/
│   ├── pom.xml
│   ├── mvnw / mvnw.cmd                 ← Maven Wrapper
│   └── src/main/
│       ├── java/com/supermarket/
│       │   ├── Category/    ┐
│       │   ├── Client/      │  cada carpeta tiene:
│       │   ├── Product/     │  controller · dto · entity
│       │   ├── Sale/        │  repository · service
│       │   ├── SaleDetail/  │
│       │   ├── Supplier/    ┘
│       │   └── SupermarketApplication.java
│       └── resources/
│           └── application.yaml        ← configuración (puerto y base de datos)
└── 📄 README.md
```

**Cómo fluye una petición:** `Controller` → `Service` → `Repository` → Base de datos.

---

## 🗃️ Modelo de datos

```mermaid
erDiagram
    CATEGORY ||--o{ PRODUCT : "clasifica"
    SUPPLIER ||--o{ PRODUCT : "suministra"
    CLIENT   ||--o{ SALE : "realiza"
    SALE     ||--|{ SALE_DETAIL : "contiene"
    PRODUCT  ||--o{ SALE_DETAIL : "se vende en"

    CATEGORY {
        bigint id PK
        varchar name
        varchar description
    }
    SUPPLIER {
        bigint id PK
        varchar name
        varchar phone
        varchar email
    }
    CLIENT {
        bigint id PK
        varchar name
        varchar document UK
        varchar phone
    }
    PRODUCT {
        bigint id PK
        varchar name
        decimal price
        int stock
        varchar barcode UK
        bigint id_category FK
        bigint id_supplier FK
    }
    SALE {
        bigint id PK
        timestamp sale_date
        decimal total
        bigint id_client FK
    }
    SALE_DETAIL {
        bigint id PK
        int amount
        decimal unit_price
        decimal subtotal
        bigint id_product FK
        bigint id_sale FK
    }
```

---

## ✅ Requisitos previos

Antes de empezar, instala:

| Herramienta | Versión | Verificar con |
|---|---|---|
| ☕ JDK | 21 o superior | `java -version` |
| 🐘 PostgreSQL | 14 o superior | `psql --version` |
| 📮 Postman | Última | — |
| 💻 VS Code (opcional) | Con *Extension Pack for Java* | — |

> 💡 No necesitas instalar Maven, el proyecto trae su propio Maven Wrapper (`mvnw`). La primera vez descarga Maven y las dependencias, así que necesitas conexión a internet.

---

## 🚀 Instalación y ejecución

### 1️⃣ Descargar el proyecto

```bash
git clone <URL-DEL-REPOSITORIO>
cd Supermarket_system
```

### 2️⃣ Crear la base de datos

El proyecto usa `ddl-auto: none`, es decir, **Spring Boot no crea las tablas**. Hay que crearlas con el script.

**Opción A: desde la terminal (psql)**

```bash
# Crear la base de datos
psql -U postgres -c "CREATE DATABASE supermarket;"

# Crear las tablas
psql -U postgres -d supermarket -f Database/db_supermarket.sql
```

**Opción B: desde pgAdmin o DBeaver**

1. Clic derecho en **Databases** → **Create** → **Database** y escribe `supermarket`.
2. Abre una consola de consultas (*Query Tool*) sobre esa base de datos.
3. Abre el archivo `Database/db_supermarket.sql` y ejecútalo completo (**F5**).

✅ Al terminar deben existir 6 tablas: `category`, `supplier`, `client`, `product`, `sale` y `sale_detail`.

### 3️⃣ Configurar la conexión

Abre `backend/src/main/resources/application.yaml` y ajusta tu usuario y contraseña de PostgreSQL:

```yaml
spring:
  application:
    name: supermarket

  datasource:
    url: jdbc:postgresql://localhost:5432/supermarket
    username: postgres               # 👈 tu usuario
    password: TU_CONTRASEÑA          # 👈 tu contraseña
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: none
    show-sql: true

server:
  port: 6060                         # 👈 puerto de la API
```

> ⚠️ En el repositorio el campo `password` viene vacío (`#contraseña personal`). Si no lo completas, la app no podrá conectarse a la base de datos.

### 4️⃣ Ejecutar el backend

**Desde la terminal**

```bash
cd backend

# Windows (PowerShell o CMD)
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

**Desde VS Code:** abre `SupermarketApplication.java` y presiona **▶ Run**.

### 5️⃣ Comprobar que funciona

Con la app corriendo, abre en el navegador:

```
http://localhost:6060/api/category
```

Si ves `[]` (lista vacía), todo salió bien. 🎉

---

## 📮 Colección de Postman

📁 Archivo: [`Colección de Postman/Supermarket_API.postman_collection.json`](Colecci%C3%B3n%20de%20Postman/Supermarket_API.postman_collection.json)

### Cómo importarla

1. Abre Postman → botón **Import**.
2. Arrastra el archivo `.json` (o elígelo con **Files**).
3. Verás la colección **Supermarket System - API REST** con 8 carpetas y 47 peticiones.

### Cómo usarla

Con el backend encendido y la base de datos creada, tienes dos opciones:

- ▶️ **Todo de una vez:** clic derecho en la colección → **Run collection** → **Run**.
- 👆 **Una por una:** sigue el orden de las carpetas.

| # | Carpeta | Qué contiene |
|---|---|---|
| 1 | 📂 Category | Crear, listar, consultar y actualizar |
| 2 | 🚚 Supplier | Crear, listar, consultar y actualizar |
| 3 | 🧑 Client | Crear, listar, consultar, actualizar y buscar por documento |
| 4 | 📦 Product | Crear 2 productos, listar, consultar, actualizar, listar por categoría, buscar por nombre y ver stock bajo |
| 5 | 🧾 Sale | Crear una venta, consultarla, verificar el stock, ventas por cliente y por rango de fechas |
| 6 | 🧩 SaleDetail | Crear, listar, consultar y actualizar detalles |
| 7 | ⚠️ Casos de error | Pruebas que deben fallar a propósito (400, 404 y 409) |
| 8 | 🧹 Eliminar | Borra los datos de prueba en el orden correcto |

### ✨ Lo que hace la colección por ti

- Guarda los ids que devuelve cada `POST` y los usa en las demás peticiones.
- Genera códigos de barras, documentos, correos y fechas únicos en cada ejecución, así que se puede correr varias veces.
- Trae **pruebas automáticas** que revisan el código de respuesta y los cálculos (por ejemplo, que el total sea 15000).

> 🌐 La variable `baseUrl` apunta a `http://localhost:6060`. Si cambias el puerto en `application.yaml`, cámbiala también en las variables de la colección.
>
> ⏭️ Para que las pruebas pasen, ejecuta las carpetas en orden (o usa **Run collection**). Si detienes la ejecución a la mitad, corre la carpeta **Eliminar** o limpia las tablas con el script antes de volver a empezar.

---

## 🔗 Endpoints

Base URL: `http://localhost:6060`

| Recurso | ➕ Crear | 📋 Listar | 🔍 Por id | ✏️ Editar | 🗑️ Eliminar |
|---|---|---|---|---|---|
| **Category** | `POST /api/category` | `GET /api/category` | `GET /api/category/{id}` | `PUT /api/category/{id}` | `DELETE /api/category/{id}` |
| **Supplier** | `POST /api/supplier` | `GET /api/supplier` | `GET /api/supplier/{id}` | `PUT /api/supplier/{id}` | `DELETE /api/supplier/{id}` |
| **Client** | `POST /api/client` | `GET /api/client` | `GET /api/client/{id}` | `PUT /api/client/{id}` | `DELETE /api/client/{id}` |
| **Product** | `POST /api/product/Post` | `GET /api/product/Get` | `GET /api/product/Get/{id}` | `PUT /api/product/Put/{id}` | `DELETE /api/product/Delete/{id}` |
| **Sale** | `POST /api/sale` | `GET /api/sale` | `GET /api/sale/{id}` | — | `DELETE /api/sale/{id}` |
| **SaleDetail** | `POST /api/saleDetail` | `GET /api/saleDetail` | `GET /api/saleDetail/{id}` | `PUT /api/saleDetail/{id}` | `DELETE /api/saleDetail/{id}` |

> 📌 Las rutas de **Product** conservan el sufijo `/Post`, `/Get`, `/Put` y `/Delete`; las demás no.

### 🔎 Consultas adicionales

| Qué hace | Endpoint |
|---|---|
| Buscar un cliente por documento | `GET /api/client/document/{document}` |
| Productos de una categoría | `GET /api/product/categoria/{idCategory}` |
| Buscar productos por nombre (sin importar mayúsculas) | `GET /api/product/search?nombre=leche` |
| Productos con stock bajo (por defecto `limit=10`) | `GET /api/product/low-stock?limit=15` |
| Ventas de un cliente | `GET /api/sale/client/{idClient}` |
| Ventas en un rango de fechas (`yyyy-MM-dd`) | `GET /api/sale/date?start=2026-10-01&end=2026-10-31` |
| Productos de una venta | `GET /api/saleDetail/sale/{saleId}` |

### 📝 Qué enviar en el cuerpo (JSON)

<details>
<summary><b>📂 Category</b></summary>

```json
{ "name": "Lácteos", "description": "Leche, queso y yogur" }
```
</details>

<details>
<summary><b>🚚 Supplier</b></summary>

```json
{ "name": "Alpina", "phone": "3101234567", "email": "ventas@alpina.com" }
```
</details>

<details>
<summary><b>🧑 Client</b></summary>

```json
{ "name": "Ana Pérez", "document": "1094123456", "phone": "3209876543" }
```
</details>

<details>
<summary><b>📦 Product</b></summary>

```json
{
  "name": "Leche 1L",
  "price": 4500.00,
  "stock": 20,
  "barcode": "7701234567890",
  "idCategory": 1,
  "idSupplier": 1
}
```
</details>

<details open>
<summary><b>🧾 Sale (venta completa)</b></summary>

```json
{
  "idClient": 1,
  "details": [
    { "idProduct": 1, "amount": 2 },
    { "idProduct": 2, "amount": 3 }
  ]
}
```

Con Leche a 4500 y Pan a 2000, `GET /api/sale/1` responde:

```json
{
  "id": 1,
  "saleDate": "2026-10-20T10:30:00",
  "total": 15000.00,
  "idClient": 1,
  "details": [
    { "id": 1, "amount": 2, "unitPrice": 4500.00, "subtotal": 9000.00, "idProduct": 1, "idSale": 1 },
    { "id": 2, "amount": 3, "unitPrice": 2000.00, "subtotal": 6000.00, "idProduct": 2, "idSale": 1 }
  ]
}
```

> El `POST /api/sale` responde con los datos de la venta (id, fecha, total y cliente); para ver los detalles consulta `GET /api/sale/{id}`.
</details>

<details>
<summary><b>🧩 SaleDetail (detalle suelto)</b></summary>

```json
{ "amount": 2, "idProduct": 1, "idSale": 1 }
```
</details>

> 🔢 `phone` y `barcode` van **entre comillas** (son texto). `price`, `stock` y los ids van sin comillas.

---

## 🧠 Reglas de negocio

**🧾 Ventas**
- Al crear una venta se valida que el cliente exista, que haya productos, que las cantidades sean mayores a 0 y que alcance el stock.
- Se **descuenta el stock** de cada producto y se guarda el **precio del momento** (`unit_price`). Si el precio cambia después, la venta no se altera.
- El `subtotal` de cada detalle es `precio × cantidad`, y el `total` es la suma de los subtotales.
- Todo ocurre en **una sola transacción**: si algo falla (por ejemplo, stock insuficiente en el segundo producto), no se guarda nada y el stock queda como estaba.
- Al **eliminar una venta** se devuelve el stock a los productos y se borran sus detalles.

**🔒 Validaciones**
- El código de barras del producto es **único** y solo acepta números (la base de datos lo exige).
- El documento del cliente es **único**; el nombre de la categoría y el correo del proveedor tampoco se pueden repetir (lo valida la API).
- Precio mayor a 0 y stock mayor o igual a 0 (el stock puede quedar en 0 al vender la última unidad).
- No se puede eliminar una **categoría** ni un **proveedor** que tengan productos.

**⚠️ Para tener en cuenta**
- Los endpoints sueltos de `SaleDetail` (`POST`, `PUT`, `DELETE`) **no** descuentan stock ni actualizan el total de la venta. Para vender, usa `POST /api/sale`.
- Para borrar un producto o un cliente que ya participó en una venta, primero elimina esas ventas (la base de datos lo impide por las llaves foráneas).
- Al editar (`PUT`) una categoría, un cliente o un proveedor, el nombre, documento o correo nuevo debe ser distinto a los que ya existen en otros registros.

---

## 🚦 Códigos de respuesta

| Código | Significado | Ejemplo |
|---|---|---|
| ✅ `200 OK` | Consulta o actualización correcta | `GET /api/product/Get` |
| 🆕 `201 Created` | Registro creado | `POST /api/sale` |
| 🗑️ `204 No Content` | Eliminado correctamente | `DELETE /api/sale/1` |
| ❌ `400 Bad Request` | Datos inválidos o repetidos | Cantidad 0, código de barras repetido |
| 🔎 `404 Not Found` | No existe el registro | `GET /api/product/Get/999999` |
| ⛔ `409 Conflict` | Choca con una regla del negocio | Stock insuficiente, categoría con productos |

---

## 🛠️ Solución de problemas

<details>
<summary><b>❌ <code>password authentication failed for user "postgres"</code></b></summary>

La contraseña de `application.yaml` no coincide con la de tu PostgreSQL. Corrígela y vuelve a ejecutar.
</details>

<details>
<summary><b>❌ <code>database "supermarket" does not exist</code></b></summary>

Falta crear la base de datos. Haz el [paso 2](#2️⃣-crear-la-base-de-datos).
</details>

<details>
<summary><b>❌ <code>relation "category" does not exist</code></b></summary>

La base existe pero no tiene tablas. Ejecuta `Database/db_supermarket.sql` conectado a `supermarket`.
</details>

<details>
<summary><b>❌ <code>relation "category" already exists</code></b></summary>

Ya ejecutaste el script antes. Si quieres empezar de cero, mira la última opción de esta lista.
</details>

<details>
<summary><b>❌ <code>psql</code> no se reconoce como comando (Windows)</b></summary>

Usa la **Opción B** del paso 2 (pgAdmin), o agrega la carpeta `bin` de PostgreSQL (por ejemplo `C:\Program Files\PostgreSQL\16\bin`) a la variable de entorno `PATH`.
</details>

<details>
<summary><b>❌ <code>Port 6060 was already in use</code></b></summary>

Hay otra aplicación usando ese puerto. Ciérrala o cambia `server.port` en `application.yaml` (y la variable `baseUrl` en Postman).
</details>

<details>
<summary><b>❌ Postman responde <code>Could not send request</code></b></summary>

El backend no está encendido, o `baseUrl` apunta a otro puerto. Revisa la consola de Spring Boot.
</details>

<details>
<summary><b>❌ Postman responde 404 en todas las peticiones</b></summary>

Estás usando una colección vieja con rutas como `/Category/Post`. Importa de nuevo el archivo actual: las rutas ahora empiezan por `/api/`.
</details>

<details>
<summary><b>❌ VS Code marca error en los getters y setters</b></summary>

Es Lombok. Instala *Extension Pack for Java*, y luego ejecuta **Java: Clean Java Language Server Workspace** desde la paleta de comandos (`Ctrl + Shift + P`).
</details>

<details>
<summary><b>🔄 Quiero borrar todo y empezar de cero</b></summary>

En `Database/db_supermarket.sql` quita el `--` de las seis líneas `DROP TABLE` (sección *Reiniciar desde cero*) y ejecuta el script completo. Ojo: se pierden todos los datos.
</details>

---

<div align="center">

📚 Proyecto académico · Análisis y Desarrollo de Software (ADSO) · SENA

Hecho con ☕ y mucho Spring Boot

</div>
