CREATE TABLE IF NOT EXISTS shopping_carts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR NOT NULL UNIQUE,
    state VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS cart_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cart_id UUID NOT NULL REFERENCES shopping_carts(id) ON DELETE CASCADE,
    product_id UUID NOT NULL,
    quantity BIGINT NOT NULL,
    CONSTRAINT unique_product_in_cart UNIQUE (cart_id, product_id)
)
