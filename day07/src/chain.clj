(ns chain
    (require [clojure.java.io :as io]
             [clojure.string :as string]
             [clojure.set :refer [map-invert]]))

(defrecord Step [dependency then])

(defn parse [entry]
  (rest (re-find #"^Step ([A-Z]) must be finished before step ([A-Z]) can begin." entry)))

(def puzzle-input
  (with-open [r (io/reader "input.txt")]
    (->> r line-seq (map parse) (map #(apply ->Step %)) doall)))

(def puzzle-letters
  (sort-by > (distinct (flatten (map #(vector (:dependency %) (:then %)) puzzle-input)))))


(defn update-all
  "Update all values of dataMap according to f"
  [f map]
  (reduce-kv (fn [m k v] (assoc m k (f v))) {} map))


(defn keys-by-value
  "Get all keys in map by value"
  [val map]
  (filter (comp #{val} map) (keys map)))


(defn remove-value
  "Remove value from sequence"
  [val coll]
  (remove #(= val %) coll))

;;;;;;;;;;;;;;
;; Puzzle 1 ;;
;;;;;;;;;;;;;;

(defn get-dependencies
  "Get a map of all letters with their dependencies"
  [steps]
  (let [get-dependency  (fn [l] (map :dependency (filter #(= l (:then %)) steps)))]
    (zipmap puzzle-letters (map get-dependency puzzle-letters))))


(defn find-chain
  [steps]
  (loop [chain     ""
         nextSteps (get-dependencies steps)]
    (if (empty? nextSteps)
      chain
      (let [next (first (sort (keys-by-value '() nextSteps)))]
        (recur (str chain next) (update-all #(remove-value next %) (dissoc nextSteps next)))))))


(defn -main[]
  (println "Puzzle 1" (find-chain puzzle-input)))
