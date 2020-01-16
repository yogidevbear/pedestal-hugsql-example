-- :name products :? :*
-- :doc Get all products
SELECT * FROM products;

-- :name product :? :1
-- :doc Get product by id
SELECT * FROM products WHERE id = :id;

-- :name new-product :insert :1
INSERT INTO
products(name, availability, price)
VALUES(:name, :availability, :price)
RETURNING id;

-- :name update-product :! :n
UPDATE products
SET name = :name,
    availability = :availability,
    price = :price
WHERE id = :id;

-- :name delete-product :! :n
-- :doc Delete product by id
DELETE FROM products WHERE id = :id;