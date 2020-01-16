(ns shop-api.products
  (:require [shop-api.sql.products :as sql]
            [shop-api.config :refer [db]]
            [io.pedestal.http :as http]
            [clojure.spec.alpha :as s]))

(defn all-products [_]
  (http/json-response (sql/products db)))

(defn get-product [request]
  (http/json-response (sql/product db {:id (Integer. (get-in request [:path-params :id]))})))

(s/def ::id int?)
(s/def ::name string?)
(s/def ::availability int?)
(s/def ::price (s/or :price int?
                     :price float?))

(s/def ::new-product (s/keys :req-un [::name ::availability ::price]))
(s/def ::update-product (s/keys :req-un [::id ::name ::availability ::price]))


(defn create-product [request]
  (let [new-product (select-keys (:json-params request)
                              [:name :availability :price])]
    (if (s/valid? ::new-product new-product)
      (let [[_ id] (sql/new-product db new-product)]
        (http/json-response {:msg "Product created successfully."
                             :id id}))
      (assoc (http/json-response {:msg "Please send a valid product."})
             :status 400))))

(defn update-product [request]
  (let [product (assoc (select-keys (:json-params request)
                                     [:name :availability :price])
                        :id (Integer. (get-in request [:path-params :id])))]
    (if (s/valid? ::update-product product)
      (let [result (sql/update-product db product)
            msg (if (= 1 result) "Product updated successfully" "Failed to update product.")]
        (http/json-response {:msg msg}))
      (assoc (http/json-response {:msg "Please send a valid product."})
             :status 400))))

(defn delete-product [request]
  (let [result (sql/delete-product db {:id (Integer. (get-in request [:path-params :id]))})
        msg (if (= 1 result) "Product delete successfully" "Failed to delete product.")]
    (http/json-response {:msg msg})))

(comment
  (do
    (require '[shop-api.config :refer [db]])
    (require '[shop-api.sql.products :as sd] :reload)
    (require '[shop-api.products :as d] :reload))
  (s/valid? ::new-product {:name "Foo" :availability 100 :price 10})
  (s/valid? ::update-product {:id 11 :name "Foo" :availability 100 :price 12.26})
  (sd/product db {:id (Integer. "11")})
  (sd/update-product db {:name "Non Existant (edited)" :availability 103 :price 56.13 :id 11}))
;curl -X POST -H 'Content-Type: application/json' -i http://localhost:8890/products --data '{"name": "Non Existant", "availability": 104, "price": 56.79}'
;curl -X POST -H 'Content-Type: application/json' -i http://localhost:8890/product/12 --data '{"name": "Non Existant (edited)", "availability": 103, "price": 43.21}'
;curl -X DELETE -H 'Content-Type: application/json' -i http://localhost:8890/product/12