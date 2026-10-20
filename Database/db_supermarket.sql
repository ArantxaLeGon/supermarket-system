-- =====================================================================
--  SUPERMARKET SYSTEM · Script de base de datos
--  Motor: PostgreSQL 14 o superior
--  Crea las 6 tablas: category, supplier, client, product, sale, sale_detail
-- =====================================================================
--
--  CÓMO USARLO
--  1) Crear la base de datos (una sola vez). Desde psql:
--         CREATE DATABASE supermarket;
--     o desde pgAdmin / DBeaver: clic derecho en Databases > Create > Database.
--  2) Conectarse a la base "supermarket" y ejecutar ESTE archivo completo:
--         psql -U postgres -d supermarket -f Database/db_supermarket.sql
--
--  Nota: el backend usa ddl-auto: none, así que Spring Boot NO crea las
--  tablas. Hay que crearlas con este script antes de arrancar la API.
-- =====================================================================


-- ---------------------------------------------------------------------
-- REINICIAR DESDE CERO (opcional)
-- Quita el comentario (--) de las 6 líneas siguientes para borrar todo y
-- volver a crear las tablas. OJO: se pierden todos los datos.
-- ---------------------------------------------------------------------
-- DROP TABLE IF EXISTS sale_detail;
-- DROP TABLE IF EXISTS sale;
-- DROP TABLE IF EXISTS product;
-- DROP TABLE IF EXISTS client;
-- DROP TABLE IF EXISTS supplier;
-- DROP TABLE IF EXISTS category;


-- CREACIÓN DE TABLAS --

-- tabla de categoria
CREATE TABLE category (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(150),

    CONSTRAINT pk_category_id PRIMARY KEY (id)
);

-- tabla de proveedor
CREATE TABLE supplier(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(70),

    CONSTRAINT pk_supplier_id PRIMARY KEY (id)
);

-- tabla de cliente
CREATE TABLE client(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(70) NOT NULL,
    document VARCHAR(25) NOT NULL,
    phone VARCHAR(15) NOT NULL,

    CONSTRAINT pk_client_id PRIMARY KEY (id),
    CONSTRAINT uq_client_document UNIQUE (document)
);

-- tabla de producto
CREATE TABLE product(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(40) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL,
    barcode VARCHAR(14),
    id_category BIGINT NOT NULL,
    id_supplier BIGINT NOT NULL,

    CONSTRAINT pk_product_id PRIMARY KEY (id),
    CONSTRAINT uq_product_barcode UNIQUE (barcode),
    CONSTRAINT fk_product_id_category FOREIGN KEY (id_category) REFERENCES category(id),
    CONSTRAINT fk_product_id_supplier FOREIGN KEY (id_supplier) REFERENCES supplier(id),
    CONSTRAINT ck_product_price CHECK (price > 0),
    CONSTRAINT ck_product_stock CHECK (stock >= 0),
    CONSTRAINT ck_product_barcode CHECK (barcode ~ '^[0-9]+$')

);

-- tabla de ventas
CREATE TABLE sale(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    sale_date TIMESTAMP NOT NULL DEFAULT NOW(),
    total DECIMAL(10,2) NOT NULL,
    id_client BIGINT NOT NULL,

    CONSTRAINT pk_sale_id PRIMARY KEY (id),
    CONSTRAINT fk_sale_id_client FOREIGN KEY (id_client) REFERENCES client(id)
);

-- tabla de detalles de venta
CREATE TABLE sale_detail(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    amount INTEGER NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    id_product BIGINT NOT NULL,
    id_sale BIGINT NOT NULL,

    CONSTRAINT pk_sale_detail_id PRIMARY KEY (id),
    CONSTRAINT fk_sale_detail_id_product FOREIGN KEY (id_product) REFERENCES product(id),
    CONSTRAINT fk_sale_detail_id_sale FOREIGN KEY (id_sale) REFERENCES sale(id)
);
