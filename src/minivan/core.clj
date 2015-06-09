(ns minivan.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.data.json :as json]))

(def flavors ;; TODO these should be configurable, I guess
  {:flavors [{:id 100 :name "small" :ram 1024 :vcpus 1 :swap 0 :rxtx_factor 1 :disk 10}
             {:id 200 :name "big"   :ram 4096 :vcpus 2 :swap 0 :rxtx_factor 1 :disk 20}]})

(defn json-resp [thing]
  {:headers {"content-type" "application/json"}
   :body (json/write-str thing)})

(defroutes app
  (GET "/" [] {:headers {"content-type" "text/plain"}
               :body "Hello from minivan!\n"})
  (GET "/flavors/detail" [] (json-resp flavors))
  (route/not-found "<h1>:(</h1>"))



