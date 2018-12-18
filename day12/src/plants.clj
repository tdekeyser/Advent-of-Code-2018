(ns plants
    (require [clojure.java.io :as io]
             [clojure.string :as string]))

(defrecord State [start content])
(defrecord Rule [from to])

(defn get-puzzle-input [file]
  (with-open [r (io/reader file)] (->> r line-seq doall)))

(defn parse [regex entry]
  (rest (re-find regex entry)))

(defn wrap-into
  "Returns (str wrap s wrap)"
  [wrap ^String s]
  (apply str wrap (concat s wrap)))

(defn get-initial-state [input]
  (->> (first input) (parse #"initial state: (.+)") (apply str) (wrap-into "...") (->State -3)))

(defn get-rules [input]
  (->> (drop 2 input) (map #(parse #"([#.]{5}) => ([#.])" %)) (map #(apply ->Rule %))))


(defn subs*
  "Same as subs, but pads with dots (.) upon IndexOutOfBounds.
  Won't work when start AND end is OutOfBounds, but that won't happen here :)"
  [^String s start end]
  (cond (< start 0)       (let [valStart (Math/abs start)]
                            (subs (apply str (apply str (repeat valStart ".")) s) 0 (+ valStart end)))
        (> end (count s)) (subs (apply str s (repeat (- end (count s)) ".")) start end)
        :else             (subs s start end)))


(defn apply-rules
  "Find and apply rule that matches input s. If none matches, returns middle char ((count s) = 5)."
  [rules ^String s]
  (let [rule (first (filter #(= s (:from %)) rules))]
    (if (nil? rule) "." (:to rule))))


(defn evolve
  [[^State state rules]]
  [(->> (:content state)
        (map-indexed (fn [i _] (subs* (:content state) (- i 2) (+ i 3))))
        (map #(apply-rules rules %))
        (wrap-into "..")
        (->State (- (:start state) 2)))
   rules])


(defn evolve-n
  [n init]
  (first (nth (iterate evolve init) n)))


(defn count-state
  [^State state]
  (let [{:keys [start content]} state]
    (->> (range start (inc (count content)))
         (mapv vector (string/split content #""))
         (filter #(= "#" (first %)))
         (map last)
         (reduce +))))


(defn -main []
  (let [puzzle-input  (get-puzzle-input "input.txt")
        initial-state (get-initial-state puzzle-input)
        rules         (get-rules puzzle-input)]
    (println "Puzzle 1" (count-state (evolve-n 20 [initial-state rules])))))

;; 1816
