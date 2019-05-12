(ns chronal
    (require [clojure.java.io :as io]
             [clojure.string :as string]
             [clojure.math.combinatorics :as comb]))


(def puzzle-input
  (let [data (with-open [r (io/reader "input.txt")]
               (->> r line-seq (map #(string/split % #", ")) (map #(map (fn [s] (Integer. s)) %)) doall))]
    (zipmap (range (count data)) data)))


(defn update-all
  "Update all values of dataMap according to f"
  [f dataMap]
  (reduce-kv (fn [m k v] (assoc m k (f v))) {} dataMap))


(defn ->border
  [coords]
  (let [make-border (fn [pos oper] (->> coords (update-all pos) (apply oper val) val))]
    {:x [(make-border first min-key) (make-border first max-key)]
     :y [(make-border last min-key) (make-border last max-key)]}))


(defn ->area
  [border]
  (let [full-range (fn [c] (range (first c) (inc (last c))))]
    (comb/cartesian-product (full-range (:x border)) (full-range (:y border)))))


(defn manhattan-distance
  [[^long x1 ^long y1] [^long x2 ^long y2]]
  (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2))))

;;;;;;;;;;;;;;
;; Puzzle 1 ;;
;;;;;;;;;;;;;;

(defn closest-by-index
  "Return key of closest coord; returns nil if multiple are closest."
  [coords point]
  (let [dists    (update-all (partial manhattan-distance point) coords)
        min-dist (apply min-key val dists)
        min-keys (filter (comp #{(val min-dist)} dists) (keys dists))]
    (if (= 1 (count min-keys)) (key min-dist) nil)))


(defn on-border?
  [border coord]
  (let [on? (fn [b c] (map #(= c %) (b border)))]
    (some true? (concat (on? :x (first coord)) (on? :y (last coord))))))


(defn chronal-area
  [coords]
  (let [border          (->border coords)
        area            (->area border)
        area-closest    (map #(closest-by-index coords %) area)
        keys-on-border  (->> (map #(on-border? border %) area)
                             (mapv vector area-closest)
                             (remove #(nil? (last %)))
                             (map first)
                             distinct)]
    (as-> (frequencies area-closest) $
          (apply dissoc $ keys-on-border)
          (apply max-key val $))))

;;;;;;;;;;;;;;
;; Puzzle 2 ;;
;;;;;;;;;;;;;;

(defn safe?
  [threshold coords point]
  (->> (vals coords) (map #(manhattan-distance point %)) (reduce +) (> threshold)))


(defn chronal-safe
  [threshold coords]
  (->> coords ->border ->area (filter #(safe? threshold coords %)) count))


(defn -main []
  (println "Puzzle 1" (chronal-area puzzle-input)) ;; 3293
  (println "Puzzle 2" (chronal-safe 10000 puzzle-input))) ;; 45176
