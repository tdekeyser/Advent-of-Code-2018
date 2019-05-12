(defproject Advent-of-Code-2018-day02 "0.1.0-SNAPSHOT"
  :description "Advent of code 2018 day07 playground!"
  :license
  {:name "Eclipse Public License"
   :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.logic "0.8.11"]]

  :source-paths ["src"]
  :test-paths ["test"]

  :main ^:skip-aot chain

  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})