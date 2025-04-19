CREATE TABLE sales (
  id BIGSERIAL PRIMARY KEY,
  dish_identifier BIGINT,
  sales_point VARCHAR(255),
  dish_name VARCHAR(255),
  quantity_sold BIGINT,
  unit_price DECIMAL(10, 2),
  synchronized_at TIMESTAMP NOT NULL DEFAULT NOW()
);