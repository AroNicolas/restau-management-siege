CREATE TABLE best_processing_times (
  id BIGSERIAL PRIMARY KEY,
  updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
  dish_identifier BIGINT,
  sales_point VARCHAR(255),
  dish_name VARCHAR(255),
  preparation_duration INTERVAL,
  duration_unit VARCHAR(10) CHECK (duration_unit IN ('SECONDS', 'MINUTES', 'HOUR')),
  calculation_mode VARCHAR(10) CHECK (calculation_mode IN ('AVERAGE', 'MINIMUM', 'MAXIMUM'))
);