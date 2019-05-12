(ns flow
    (:require [aoc_utils :as u]
              [operations :refer :all]))

(def input (u/readlines "input.txt"))

(def instr-pointer-position
  (->> (first input) (u/parse #"#ip (\d)") (u/->int) first))

(def operations
  (->> (rest input)
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
  (loop [register [1 0 0 0 0 0]
         step     0]
    (let [ip      (get register p)]
      (if (or (< (count operations) ip))
        register
        (do
          (if (= 0 (mod step 1000000)) (println register))
          (recur (update-ip p (call (get operations ip) register)) (inc step)))))))


(defn -main []
  (println "Puzzle 1" (time (execute-program operations instr-pointer-position))))

;; Puzzle 1 [1620 1 986 987 257 987]
