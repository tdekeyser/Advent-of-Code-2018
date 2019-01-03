(ns flow
    (require [aoc_utils :as u]
             [operations :refer :all]))

(def puzzle-init
  (->> (u/readlines "input.txt") first (u/parse #"#ip (\d)") (u/->int) first))

(def puzzle-input
  (->> (u/readlines "input.txt")
       rest
       (map #(u/parse #"([a-z]+) (\d+) (\d+) (\d+)" %))
       (map #(vector (first %) (concat [0] (into [] (map u/->int (rest %))))))
       (into [])))


(defn- call* [^String fcn & args]
  "Call function based on its string name"
  (apply (resolve (symbol fcn)) args))


(defn call
  "Call operation o with instruction i on register r"
  [[o i] r]
  (call* o r i))


(defn update-ip
  "Increment instruction pointer at position p"
  [p register]
  (assoc register p (inc (get register p))))


(defn execute-program
  "Execute operations with instruction pointer position p"
  [operations p]
  (loop [register [0 0 0 0 0 0]]
    (let [ip      (get register p)]
      (if (or (< (count operations) ip))
        register
        (recur (update-ip p (call (get operations ip) register)))))))


(defn -main []
  (println "Puzzle 1"))
