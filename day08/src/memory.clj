(ns memory
    (require [clojure.java.io :as io]
             [clojure.string :as cljstr]))

(def puzzle-input
  (with-open [r (io/reader "input.txt")]
    (->> r line-seq (map #(cljstr/split % #" ")) doall first (map #(Integer/parseInt %)))))

;;;;;;;;;;;;;;
;; Puzzle 1 ;;
;;;;;;;;;;;;;;

(defn count-metadata
  [memory]
  (loop [metadata  0
         children  (first memory)
         metacount (second memory)
         mem       (drop 2 memory)]
    (cond (= children 0)  [(+ metadata (reduce + (take metacount mem)))
                           (drop metacount mem)]
          :else           (let [[child-metadata remain] (count-metadata mem)]
                            (recur (+ metadata child-metadata) (dec children) metacount remain)))))

;;;;;;;;;;;;;;
;; Puzzle 2 ;;
;;;;;;;;;;;;;;

(defn nth-safe [coll i] (if (> (count coll) i -1) (nth coll i) 0))

(defn count-value
  [memory]
  (let [children    (first memory)
        metacount   (second memory)]
    (loop [mem      (drop 2 memory)
           child    0
           values   []]
      (cond (= children 0)     (count-metadata memory)
            (= children child) [(->> (take metacount mem) (map dec) (map #(nth-safe values %)) (reduce +))
                                (drop metacount mem)]
            :else              (let [[value remain] (count-value mem)]
                                 (recur remain (inc child) (conj values value)))))))

(defn -main[]
  (println "Puzzle 1 (solution = 45618)" (count-metadata puzzle-input))
  (println "Puzzle 2 (solution = 22306)" (count-value puzzle-input)))
