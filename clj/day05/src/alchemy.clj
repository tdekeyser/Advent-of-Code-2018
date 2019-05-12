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

;;;;;;;;;;;;;;;;
;; Puzzle 1++ ;;
;;;;;;;;;;;;;;;;

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(defn react**
  [polymer]
  (loop [reaction polymer
         letters  alphabet]
    (let [letter      (first letters)
          newReaction (as-> reaction $
                            (do (println (str letter (cljstr/upper-case letter))) (flush) (cljstr/replace $ (str letter (cljstr/upper-case letter)) ""))
                            (cljstr/replace $ (str (cljstr/upper-case letter) letter) ""))]
      (if (= (count newReaction) (count reaction))
        reaction
        (recur
          newReaction
          (if (empty? letters) (rest letters) alphabet))))))

;;;;;;;;;;;;;;
;; Puzzle 2 ;;
;;;;;;;;;;;;;;

(defn react*
  [polymer]
  (->> alphabet
       (map
        #(as-> polymer $
          (cljstr/replace $ (str %) "")
          (cljstr/replace $ (cljstr/upper-case (str %)) "")
          (react** $)
          (count $)))
       (apply min)))


(def puzzle-input
  (with-open [r (io/reader "input.txt")]
    (->> r line-seq first doall)))


(def react-puzzle
  (react** puzzle-input))


(defn -main []
  (println "Puzzle 1:" (count react-puzzle))
  (println "Puzzle 2:" (react* react-puzzle)))
