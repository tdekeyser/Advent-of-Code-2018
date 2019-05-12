;; Quick solution.. not efficient, and not pretty either. Needs some refactoring!

(ns slice
    (require [clojure.string :as cljstr]
             [clojure.java.io :as io]
             [clojure.set]))


(defn parse
  [entry]
  (->> entry
       (re-find #"^#\d+ [@] (\d+),(\d+): (\d+)x(\d+)$")
       rest
       (map #(Integer. %))
       (zipmap [:x :y :w :h])))


(def puzzle-input
  (with-open [r (io/reader "input.txt")]
    (->> r line-seq (map parse) doall)))

;;;;;;;;;;;;;;
;; Puzzle 1 ;;
;;;;;;;;;;;;;;

(def square-size
  (->> puzzle-input
       (map #(+ (:x %) (:w %)))
       (apply max)))


(defn bool->int
  [bool]
  (if bool 1 0))


(defn contained-in?
  "Returns ((X <= x <= X+W) && (Y <= y <= Y+H))"
  [x y entry]
  (and
   (or (<= (:x entry) x (+ (:x entry) (dec (:w entry)))))
   (or (<= (:y entry) y (+ (:y entry) (dec (:h entry)))))))


(defn overlap?
  "Return true if square with dim x and y is overlapped by >= 2 entries."
  [x y entries]
  (loop [e         entries
         contained 0]
    (cond (> contained 1) true
          (empty? e)      false
          :else           (recur
                            (rest e)
                            (+ contained (bool->int (contained-in? x y (first e))))))))


(defn count-overlap
  [square-size entries]
  (loop [x           (range square-size)
         overlap     0]
    (cond (empty? x) overlap
          :else      (recur
                       (rest x)
                       (+ overlap
                          (->> (range square-size)
                               (map #(overlap? (first x) % entries))
                               (map bool->int)
                               (apply +)))))))

;;;;;;;;;;;;;;
;; Puzzle 2 ;;
;;;;;;;;;;;;;;

(defn generate-coords
  [entry]
  (let [x-range (range (:x entry) (+ (:x entry) (:w entry)))
        y-range (range (:y entry) (+ (:y entry) (:h entry)))]
    (loop [x      x-range
           coords []]
      (if (empty? x)
        coords
        (recur
          (rest x)
          (concat coords (map #(vector (first x) %) y-range)))))))


(defn has-overlap?
  [entry entries]
  (empty?
   (apply concat (map #(clojure.set/intersection (set entry) (set %)) (remove #(= entry %) entries)))))


(defn find-non-overlap
  [entries]
  (let [coords (map generate-coords entries)]
    (loop [i     0
           coord coords]
      (do (println i) (flush)
        (cond (has-overlap? (first coord) coords) i
              :else                               (recur (inc i) (rest coord)))))))


(defn -main []
  (println "Square size:" square-size)
  (println "Puzzle. Nr of overlapping squares:" (count-overlap square-size puzzle-input))
  (println "Puzzle 2: Non-overlapping element:" (find-non-overlap puzzle-input)))
