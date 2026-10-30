CREATE DATABASE IF NOT EXISTS shopping_db;

USE shopping_db;

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    price DOUBLE NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE IF NOT EXISTS customer_orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255),
    email VARCHAR(255),
    product_id BIGINT,
    quantity INT,
    total_amount DOUBLE,
    status VARCHAR(50),
    order_date DATETIME
);