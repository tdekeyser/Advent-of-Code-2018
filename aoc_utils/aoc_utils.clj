(ns aoc_utils
    (require [clojure.java.io :as io]))


(defn readlines
  "Reads and persists all lines to seq in memory."
  [filename]
  (with-open [reader (io/reader filename)]
    (doall (line-seq reader))))


(defn parse
  "Returns matching groups of regex in string."
  [regex string]
  (rest (re-find regex string)))


(defn ->int
  "Map (sequence of) string to (sequence of) int. Slow implementation, but convenient."
  [s]
  (cond (string? s) (Integer/parseInt s)
        :else       (map #(->int %) s)))


(defn update-all
  "Update all values of dataMap according to f"
  [f map]
  (reduce-kv (fn [m k v] (assoc m k (f v))) {} map))


(defn remove-value
  "Remove value from sequence"
  [val coll]
  (remove #(= val %) coll))
