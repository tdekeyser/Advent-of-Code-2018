(defproject Advent-of-Code-2018-day02 "0.1.0-SNAPSHOT"
  :description "Advent of code 2018 day16 playground!"
  :license
  {:name "Eclipse Public License"
   :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.logic "0.8.11"]]

  :source-paths ["src" "../aoc_utils"]
  :test-paths ["test"]

  :main ^:skip-aot classification

  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})