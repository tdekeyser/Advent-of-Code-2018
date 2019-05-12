(defproject Advent-of-Code-2018-day01 "0.1.0-SNAPSHOT"
  :description "Advent of code 2018 day01 playground!"
  :license
  {:name "Eclipse Public License"
   :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]

  :source-paths ["src"]
  :test-paths ["test"]

  :main ^:skip-aot frequency

  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})