CREATE TABLE IF NOT EXISTS users (
                       id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       name        VARCHAR(100) NOT NULL,
                       email       VARCHAR(255) NOT NULL UNIQUE,
                       password    VARCHAR(255) NOT NULL,   -- bcrypt
                       role        VARCHAR(20)  NOT NULL DEFAULT 'USER', -- USER | ADMIN
                       created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
                       updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS products (
                          id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          name        VARCHAR(255) NOT NULL,
                          description TEXT,
                          price       NUMERIC(12,2) NOT NULL CHECK (price >= 0),
                          stock       INTEGER       NOT NULL DEFAULT 0 CHECK (stock >= 0),
                          category    VARCHAR(100),
                          created_at  TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
                          updated_at  TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS orders (
                        id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        user_id     UUID NOT NULL REFERENCES users(id),
                        status      VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    -- PENDING | CONFIRMED | SHIPPED | DELIVERED | CANCELLED
                        total       NUMERIC(14,2) NOT NULL,
                        created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
                        updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS order_items (
                             id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             order_id    UUID         NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                             product_id  UUID         NOT NULL REFERENCES products(id),
                             quantity    INTEGER      NOT NULL CHECK (quantity > 0),
                             unit_price  NUMERIC(12,2) NOT NULL   -- snapshot at time of order
);

CREATE INDEX IF NOT EXISTS idx_orders_user_id ON orders(user_id);
CREATE INDEX IF NOT EXISTS idx_order_items_order_id ON order_items(order_id);
CREATE INDEX IF NOT EXISTS idx_products_category ON products(category);