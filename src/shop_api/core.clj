(ns shop-api.core
  (:require [io.pedestal.http :as http]
            [shop-api.products]
            [io.pedestal.http.body-params :refer [body-params]]))

(def numeric #"[0-9]+")
(def id {:id numeric})

(def routes
  #{["/products" :get shop-api.products/all-products :route-name :get-products]
    ["/products" :post
     [(body-params) shop-api.products/create-product]
     :route-name :post-products]
    ["/product/:id" :get shop-api.products/get-product
     :route-name :get-product
     :constraints id]
    ["/product/:id" :post
     [(body-params) shop-api.products/update-product]
     :route-name :update-product
     :constraints id]
    ["/product/:id" :delete shop-api.products/delete-product
     :route-name :delete-product]})

(def service-map
  {::http/routes routes
   ::http/type   :jetty
   ::http/port   8890})

(defn start-server []
  (http/start (http/create-server
               (assoc service-map
                      ::http/join? false))))