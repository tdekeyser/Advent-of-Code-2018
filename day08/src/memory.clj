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
  (loop [mem       (drop 2 memory)
         children  (first memory)
         read-meta (second memory)
         metadata  0]
    (cond (= read-meta 0) (if (empty? mem) metadata [mem metadata])
          (> children 0)  (let [[remain child-metadata] (count-metadata mem)]
                            (recur remain (dec children) read-meta (+ metadata child-metadata)))
          (> read-meta 0) (recur (drop read-meta mem) children 0 (+ metadata (reduce + (take read-meta mem)))))))

;;;;;;;;;;;;;;
;; Puzzle 2 ;;
;;;;;;;;;;;;;;



(defn -main[]
  (println "Puzzle 1" (count-metadata puzzle-input)))
