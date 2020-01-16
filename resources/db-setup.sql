CREATE TABLE products (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255),
  availability INT NOT NULL DEFAULT 0,
  price FLOAT
);

INSERT INTO products (name, availability, price)
VALUES
  ('Pasta', 100, 1.50),
  ('Rye bread', 200, 1.45),
  ('Tinned tomato', 150, 0.99),
  ('Apples', 200, 1.19),
  ('Oranges', 50, 2),
  ('Apple juice', 47, 7);