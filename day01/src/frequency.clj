(ns frequency
    (:require
      [clojure.string :as string]
      [clojure.java.io :as io]))


;; Input file contains numbers as string. Positive numbers
;; are represented as "+n", and negative numbers as "-n".
(defn str->num
  [s]
  (Integer/parseInt (string/replace s "+" "")))


(def input-as-nums
  (with-open [r (io/reader "input.txt")]
    (map #(str->num %)
         (doall (line-seq r)))))


(defn add-frequency
  "Day01 exercise 1"
  [f]
  (reduce + f))


(defn in?
  "true if coll contains ele"
  [coll ele]
  (some #(= ele %) coll))


(defn return-if-not-empty
  [coll else-coll]
  (if (not (empty? coll)) coll else-coll))


(defn first-double-frequency
  "Day01 exercise 2"
  [change-list]
  (loop [freqs       [0]
         changes     change-list]
    (let [frequency (+ (first changes) (last freqs))]
      (if (in? freqs frequency)
        frequency
        (recur
          (conj freqs frequency)
          (return-if-not-empty (rest changes) change-list))))))


(defn -main []
  (print "Day01 exercise 1: " (add-frequency input-as-nums) "\n")
  (print "Day01 exercise 2: " (first-double-frequency input-as-nums) "\n"))
