CREATE TABLE IF NOT EXISTS warehouse_products (
    product_id UUID PRIMARY KEY,
    fragile BOOLEAN,
    width DOUBLE PRECISION NOT NULL,
    height DOUBLE PRECISION NOT NULL,
    depth DOUBLE PRECISION NOT NULL,
    weight DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS warehouse_stock (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL UNIQUE,
    available_quantity BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES warehouse_products(product_id) ON DELETE CASCADE
);
