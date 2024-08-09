CREATE TABLE categories
(
    id              BIGSERIAL PRIMARY KEY,
    title           VARCHAR(255),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products
(
    id              BIGSERIAL PRIMARY KEY,
    title           VARCHAR(255),
    price           NUMERIC(8, 2),
    category_id     BIGINT REFERENCES categories (id),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO categories (title)
VALUES  ('Food'),
        ('Electronic'),
        ('Cookware');

INSERT INTO products (title, price, category_id)
VALUES  ('Bread', 1.35, 1),
        ('Milk', 0.99, 1),
        ('Cheese', 2.45, 1),
        ('Saucepan', 19.99, 3),
        ('Microwave oven', 45.99, 2),
        ('Kettle', 14.45, 2);

CREATE TABLE orders
(
    id              BIGSERIAL PRIMARY KEY,
    username        VARCHAR(255),
    total_price     NUMERIC(8, 2),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items
(
    id                  BIGSERIAL PRIMARY KEY,
    order_id            BIGINT REFERENCES orders (id),
    product_id          BIGINT REFERENCES products (id),
    quantity            INT,
    price_per_product   NUMERIC(8, 2),
    price               NUMERIC(8, 2),
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);