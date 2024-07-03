CREATE TABLE products(
    id      BIGSERIAL PRIMARY KEY,
    title   VARCHAR(255),
    price   INTEGER
);

INSERT INTO products (title, price)
VALUES
    ('Bread', 2),
    ('Milk', 1),
    ('Cheese', 3),
    ('Eggs', 1),
    ('Pie', 4);