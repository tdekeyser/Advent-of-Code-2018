(ns chronal
    (require [clojure.java.io :as io]
             [clojure.string :as string]
             [clojure.math.combinatorics :as comb]))


(def puzzle-input
  (let [data (with-open [r (io/reader "input.txt")]
               (->> r line-seq (map #(string/split % #", ")) (map #(map (fn [s] (Integer. s)) %)) doall))]
    (zipmap (range (count data)) data)))

;;;;;;;;;;;;;;
;; Puzzle 1 ;;
;;;;;;;;;;;;;;

(defn manhattan-distance
  [[^long x1 ^long y1] [^long x2 ^long y2]]
  (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2))))


(defn update-all
  [f dataMap]
  (reduce-kv (fn [m k v] (assoc m k (f v))) {} dataMap))


(defn closest-by-index
  "Return key of closest coord; returns nil if multiple are closest."
  [coords point]
  (let [dists    (update-all (partial manhattan-distance point) coords)
        min-dist (apply min-key val dists)
        min-keys (filter (comp #{(val min-dist)} dists) (keys dists))]
    (if (= 1 (count min-keys)) (key min-dist) nil)))


(defn define-border
  [coords]
  (let [make-border (fn [pos oper] (->> coords (update-all pos) (apply oper val) val))]
    {:x [(make-border first min-key) (make-border first max-key)]
     :y [(make-border last min-key) (make-border last max-key)]}))


(defn on-border?
  [border coord]
  (let [on? (fn [b c] (map #(= c %) (b border)))]
    (some true? (concat (on? :x (first coord)) (on? :y (last coord))))))


(defn chronal-area
  [coords]
  (let [border          (define-border coords)
        full-range      (fn [c] (range (first c) (inc (last c))))
        area            (comb/cartesian-product (full-range (:x border)) (full-range (:y border)))
        area-closest    (map #(closest-by-index coords %) area)
        keys-on-border  (->> (map #(on-border? border %) area)
                             (mapv vector area-closest)
                             (remove #(nil? (last %)))
                             (map first)
                             distinct)]
    (as-> (frequencies area-closest) $
          (apply dissoc $ keys-on-border)
          (apply max-key val $))))

(def test-area
  {:1 [1 1] :2 [1 6] :3 [8 3] :4 [3 4] :5 [5 5] :6 [8 9] :7 [2 2]})

(defn -main []
  (println "Puzzle 1" (chronal-area puzzle-input)))
