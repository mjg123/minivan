(defproject minivan "0.1.0-SNAPSHOT"
  :description "MINImal ViAble Nova"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.4"]
                 [org.clojure/data.json "0.2.6"]]
  :plugins [[lein-ring "0.9.4"]]
  :ring {:handler minivan.core/app})
