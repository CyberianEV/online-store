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
        ('Electronic');

INSERT INTO products (title, price, category_id)
VALUES  ('Bread', 1.35, 1),
        ('Milk', 0.99, 1),
        ('Cheese', 2.45, 1),
        ('Apple Pie', 3.99, 1),
        ('Cucumber', 0.35, 1),
        ('Eggs', 1.15, 1),
        ('Tomatoes 1kg', 1.10, 1),
        ('Chocolate', 1.99, 1),
        ('Chicken fillet 1kg', 3.95, 1),
        ('Scales', 8.99, 2),
        ('Mixer', 36.99, 2),
        ('Microwave oven', 45.99, 2),
        ('Kettle', 14.45, 2);

CREATE TABLE orders
(
    id                  BIGSERIAL PRIMARY KEY,
    username            VARCHAR(255),
    total_price         NUMERIC(8, 2),
    delivery_address    VARCHAR(255),
    phone_number        VARCHAR(255),
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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

CREATE TABLE roles
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users
(
    id              BIGSERIAL PRIMARY KEY,
    username        VARCHAR(40) NOT NULL,
    password        VARCHAR(90) NOT NULL,
    email           VARCHAR(60) UNIQUE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users_roles
(
    user_id         BIGINT NOT NULL REFERENCES users (id),
    role_id         BIGINT NOT NULL REFERENCES roles (id),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO roles (name)
VALUES  ('ROLE_USER'),
        ('ROLE_ADMIN');

INSERT INTO users (username, password, email)
VALUES  ('John', '$2y$10$1wwLrEcMkaClj8tl39GzLu11o9QsmkaFvIZJeY78m2AfpDQwcfPpq', 'john@domen.org'),
        ('Helen', '$2y$10$maFGCv8sC4Rw.7Oax/wMoOCB.iqMAyeR85.K6h.c3yPECMhWuhOQu', 'helen@domen.org');

INSERT INTO users_roles (user_id, role_id)
VALUES  (1, 1),
        (2, 2);