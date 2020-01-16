(ns shop-api.sql.products
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "shop_api/sql/products.sql")