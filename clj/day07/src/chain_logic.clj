(ns chain_logic
    (require [clojure.java.io :as io]
             [clojure.string :as string]
             [clojure.core.logic :refer :all]
             [clojure.core.logic.fd :as fd]))

(def alphabet "ABCDEFGHIJKLMNOPQRSTUVWXYZ")

(defn parse [entry]
  (rest (re-find #"^Step ([A-Z]) must be finished before step ([A-Z]) can begin." entry)))

(def puzzle-input
  (with-open [r (io/reader "test-input.txt")]
    (->> r line-seq (map parse) doall)))


(defn chain
  [n steps]
  (let [vars (into [] (repeatedly 6 lvar))
        str->var (fn [s] (vars (string/index-of alphabet s)))]
    (run n [sol]
         (== sol vars)
         (everyg #(fd/in % (fd/interval 0 6)) vars)
         (distincto vars)
         (everyg #(apply fd/< (map str->var %)) steps))))


(defn find-chain
  ([] (find-chain 1))
  ([n]
   (->> (chain n puzzle-input)
        (map #(zipmap % (take 6 (string/split alphabet #""))))
        (map #(into (sorted-map) %))
        (map vals)
        (map #(apply str %)))))


(defn -main[]
  (println "Puzzle 1" find-chain))
