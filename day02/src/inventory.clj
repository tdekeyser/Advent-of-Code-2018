(ns inventory
    (require [clojure.string :as cljstr]
             [clojure.java.io :as io]))

;;;;;;;;;;;;;;
;; Puzzle 1 ;;
;;;;;;;;;;;;;;

(defn bool->int
  "Returns 1 if true, else 0."
  [bool]
  (if bool 1 0))


(defn occurs-exactly?
  "Returns 1 if any letter appears exactly occ times, else 0."
  [occ message]
  (let [freq (frequencies (cljstr/split message #""))]
    (bool->int (some #(= occ (last %)) freq))))


(defn inventory-checksum
  [inventory]
  (loop [twos   0
         threes 0
         inv    inventory]
    (if (empty? inv)
      (* twos threes)
      (let [message (first inv)]
        (recur
          (+ twos (occurs-exactly? 2 message))
          (+ threes (occurs-exactly? 3 message))
          (drop 1 inv))))))


(def read-input
  (with-open [r (io/reader "input.txt")]
    (doall (line-seq r))))


(defn -main []
  (print "Puzzle 1: Checksum is " (inventory-checksum read-input)))
