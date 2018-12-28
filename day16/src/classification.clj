(ns classification
    (require [clojure.string :as string]
             [operations :refer :all]
             [aoc_utils :as u]))


(defn split-input [capture] (into [] (u/->int (string/split capture #" "))))

(def puzzle-input1
  (->> (u/readlines "input1.txt")
       (map #(u/parse #"^Before: \[([\d ]*)\]\|([\d ]*)\|After:  \[([\d ]*)\]" %))
       (map #(map split-input %))
       (map #(zipmap [:before :instr :after] %))))

(def puzzle-input2
  (->> (u/readlines "input2.txt")
       (map #(split-input %))))

(defn remove-value
  [map val]
  (u/update-all #(u/remove-value val %) map))

;;;;;;;;;;;;;;
;; Puzzle 1 ;;
;;;;;;;;;;;;;;

(def operations [addr addi mulr muli banr bani borr bori setr seti gtir gtri gtrr eqir eqri eqrr])

(defn is-operation?
  [op {before :before after :after instr :instr}]
  (= (op before instr) after))

(defn count-behave
  "Count the number of similar operation results for each code."
  [simulations]
  (let [apply-ops   (fn [ofn code] (ofn (:before code) (:instr code)))
        ops-behaves (fn [code] (map #(if (is-operation? % code) 1 0) operations))]
    (map #(reduce + (ops-behaves %)) simulations)))

;;;;;;;;;;;;;;
;; Puzzle 2 ;;
;;;;;;;;;;;;;;

(defn- refine-candidates
  [candidates {i :instr :as sim}]
  (assoc candidates (first i) (filter #(is-operation? % sim) (get candidates (first i)))))

(defn- find-candidates
  "Go over each simulation and eliminate impossible operations"
  [simulations]
  (let [all-candidates (zipmap (range (count operations)) (repeat operations))]
    (reduce refine-candidates all-candidates simulations)))

(defn- select-operations
  "Select possible operations for each opcode"
  [candidates]
  (loop [cands          candidates
         found-codes    {}]
    (if (= (count found-codes) (count operations))
      found-codes
      (let [single-code (first (filter #(= 1 (count (val %))) cands))]
        (recur
          (remove-value cands (first (val single-code)))
          (assoc found-codes (key single-code) (first (val single-code))))))))

(def ->operations
  (comp select-operations find-candidates))


(defn execute-program
  [simulations instructions]
  (let [operations (->operations simulations)
        exec-op    (fn [instr] (get operations (first instr)))]
    (reduce #((exec-op %2) %1 %2) [0 0 0 0] instructions)))


(defn -main []
  (println "Puzzle 1" (count (filter #(<= 3 %) (count-behave puzzle-input1))))
  (println "Puzzle 2" (execute-program puzzle-input1 puzzle-input2)))

;; 590
;; [475 3 2 2]
