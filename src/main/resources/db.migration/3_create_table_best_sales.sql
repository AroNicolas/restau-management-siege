CREATE TABLE best_sales (
  id BIGSERIAL PRIMARY KEY,
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  dish_identifier BIGINT,
  sales_point VARCHAR(255),
  dish_name VARCHAR(255),
  quantity_sold BIGINT,
  total_amount DECIMAL(10, 2)
);