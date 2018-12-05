(ns alchemy
    (require [clojure.java.io :as io]
             [clojure.string :as cljstr]))

;;;;;;;;;;;;;;
;; Puzzle 1 ;;
;;;;;;;;;;;;;;

(defn interact?
  [one two]
  (if (= one two)
    false
    (->> [one two]
         (map #(cljstr/upper-case %))
         (reduce =))))


(defn react-once
  [polymer]
  (loop [poly     polymer
         reaction ""]
    (cond (< (count poly) 2)                     (str reaction (first poly))
          (interact? (first poly) (second poly)) (recur (drop 2 poly) reaction)
          :else                                  (recur (rest poly) (str reaction (first poly))))))


(defn react
  [polymer]
  (loop [poly  polymer]
    (let [reaction (react-once poly)]
      (if (= (count poly) (count reaction))
        reaction
        (recur reaction)))))

;;;;;;;;;;;;;;
;; Puzzle 2 ;;
;;;;;;;;;;;;;;

(defn react*
  [polymer]
  (->> "abcdefghijklmnopqrstuvwxyz"
       (map
         #(as-> polymer $
           (cljstr/replace $ (str %) "")
           (cljstr/replace $ (cljstr/upper-case (str %)) "")
           (react $)
           (count $)))
       (apply min)))


(def puzzle-input
  (with-open [r (io/reader "input.txt")]
    (->> r line-seq first doall)))


(defn -main []
  (println "Puzzle 1:" (count (react puzzle-input)))
  (println "Puzzle 2:" (react* puzzle-input)))
