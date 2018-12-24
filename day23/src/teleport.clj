(ns teleport
    (require [clojure.java.io :as io]
             [clojure.string :as string]))

(defn parse [regex entry]
  (rest (re-find regex entry)))

(def puzzle-input
  (with-open [r (io/reader "input.txt")]
    (->> r line-seq doall
         (map #(parse #"^pos=<([-]?[0-9]*),([-]?[0-9]*),([-]?[0-9]*)>, r=([-]?[0-9]*)" %))
         (map #(map (fn [s] (Long/parseLong s)) %)))))

(def nanobots (zipmap (range (count puzzle-input)) (map butlast puzzle-input)))
(def ranges (zipmap (range (count puzzle-input)) (map last puzzle-input)))

(defn manhattan-distance
  "Calculates Manhattan distance between point1 and point2"
  [point1 point2]
  (reduce + (mapv #(Math/abs (- ^long %1 ^long %2)) point1 point2)))

;;;;;;;;;;;;;;
;; Puzzle 1 ;;
;;;;;;;;;;;;;;

(defn in-range?
  "Check if target is in range of pos"
  [range pos target]
  (>= range (manhattan-distance pos target)))

(defn positions-in-range
  [range position targets]
  (filter #(in-range? range position %) targets))

(defn count-in-range
  "Get num of bots in range of strongest bot"
  [target nanobots]
  (let [target-position    (get nanobots (key target))]
    (count (positions-in-range (val target) target-position (vals nanobots)))))



(defn -main []
  (println "Puzzle 1" (count-in-range (apply max-key val ranges) nanobots)))
