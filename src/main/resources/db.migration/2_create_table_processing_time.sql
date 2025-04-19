CREATE TABLE processing_time (
  id BIGSERIAL PRIMARY KEY,
  dish_identifier BIGINT,
  sales_point VARCHAR(255),
  dish_name VARCHAR(255),
  quantity DECIMAL(10, 2),
  started_at TIMESTAMP,
  finished_at TIMESTAMP,
  calculated_at TIMESTAMP NOT NULL DEFAULT NOW()
);