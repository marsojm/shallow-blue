(defproject shallow-blue "0.1.0-SNAPSHOT"
  :description "shallow-blue tic-tac-toe"
  :url "http://example.com/FIXME"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :main ^:skip-aot shallow-blue.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
