(ns classification
    (require [clojure.string :as string]
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


(defn greater?
  "Returns 1 if a > b, else 0"
  [a b]
  (if (> a b) 1 0))

(defn equal?
  "Returns 1 if a == b, else 0"
  [a b]
  (if (= a b) 1 0))

(defn exec
  "Sets register C to (ofn (mapa a) (mapb b))."
  [ofn r i & {:keys [mapa mapb] :or {mapa (partial get r) mapb (partial get r)}}]
  (let [[_ a b c] i]
    (assoc r c (ofn (mapa a) (mapb b)))))

(defn addr
  "add register"
  [r i]
  (exec + r i))

(defn addi
  "add immediate"
  [r i]
  (exec + r i :mapb identity))

(defn mulr
  "multiply register"
  [r i]
  (exec * r i))

(defn muli
  "multiply immediate"
  [r i]
  (exec * r i :mapb identity))

(defn banr
  "bitwise AND register"
  [r i]
  (exec bit-and r i))

(defn bani
  "bitwise AND immediate"
  [r i]
  (exec bit-and r i :mapb identity))

(defn borr
  "bitwise OR register"
  [r i]
  (exec bit-or r i))

(defn bori
  "bitwise OR immediate"
  [r i]
  (exec bit-or r i :mapb identity))

(defn setr
  "set register"
  [r i]
  (exec (fn [ra _] ra) r i))

(defn seti
  "set immediate"
  [r i]
  (exec (fn [a _] a) r i :mapa identity))

(defn gtir
  "greater-than immediate/register"
  [r i]
  (exec (fn [a rb] (greater? a rb)) r i :mapa identity))

(defn gtri
  "greater-than register/immediate"
  [r i]
  (exec (fn [ra b] (greater? ra b)) r i :mapb identity))

(defn gtrr
  "greater-than register/register"
  [r i]
  (exec (fn [ra rb] (greater? ra rb)) r i))

(defn eqir
  "equal immediate/register"
  [r i]
  (exec (fn [a rb] (equal? a rb)) r i :mapa identity))

(defn eqri
  "equal register/immediate"
  [r i]
  (exec (fn [ra b] (equal? ra b)) r i :mapb identity))

(defn eqrr
  "equal register/register"
  [r i]
  (exec (fn [ra rb] (equal? ra rb)) r i))

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
