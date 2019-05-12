(defproject Advent-of-Code-2018-day19 "0.1.0-SNAPSHOT"
  :description "Advent of code 2018 day19 playground!"
  :license
  {:name "Eclipse Public License"
   :url  "http://www.eclipse.org/legal/epl-v10.html"}

  ;; Overrides older version of rrb-vector that doesn't work on JDK 11.
  :managed-dependencies [[org.clojure/core.rrb-vector "0.0.13"]]
  :plugins [[org.clojure/core.rrb-vector "0.0.13"]]

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/core.logic "0.8.11"]]

  :source-paths ["src" "../aoc_utils" "../day16/src"]
  :test-paths ["test"]

  :main ^:skip-aot flow

  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})