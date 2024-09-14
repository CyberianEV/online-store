ALTER TABLE orders
ADD (
    delivery_address   VARCHAR(255),
    phone_number       VARCHAR(255)
)
AFTER total_price;