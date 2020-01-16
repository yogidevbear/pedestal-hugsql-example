(ns shop-api.config)

(def db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "//localhost:5432/shop_dev"
   :user "postgres"
   :password "password"})