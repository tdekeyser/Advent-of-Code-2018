(ns chain
    (require [clojure.java.io :as io]
             [clojure.string :as string]
             [clojure.set :refer [map-invert]]))

(defrecord Step [dependency then])

(defn parse [entry]
  (rest (re-find #"^Step ([A-Z]) must be finished before step ([A-Z]) can begin." entry)))

(def puzzle-input
  (with-open [r (io/reader "input.txt")]
    (->> r line-seq (map parse) (map #(apply ->Step %)) doall)))

(def puzzle-letters
  (sort-by > (distinct (flatten (map #(vector (:dependency %) (:then %)) puzzle-input)))))

(def alphabet-timer
  (zipmap (string/split "ABCDEFGHIJKLMONPQRSTUVWXYZ" #"") (map #(+ 60 %) (range 1 27))))


(defn update-all
  "Update all values of dataMap according to f"
  [f map]
  (reduce-kv (fn [m k v] (assoc m k (f v))) {} map))


(defn keys-by-value
  "Get all keys in map by value"
  [val map]
  (filter (comp #{val} map) (keys map)))


(defn remove-value
  "Remove value from sequence"
  [val coll]
  (remove #(= val %) coll))


(defn remove-keys
  "Remove all key-values from map"
  [map keys]
  (reduce #(dissoc %1 %2) map keys))


(defn add-keys
  "Add all key values to map. Values are computed as (vf k)."
  [map keys vf]
  (reduce #(assoc %1 %2 (vf %2)) map keys))


(defn min-val
  "Find the minimal value in map"
  [map]
  (val (apply min-key val map)))

;;;;;;;;;;;;;;
;; Puzzle 1 ;;
;;;;;;;;;;;;;;

(defn get-dependencies
  "Get a map of all letters with their dependencies"
  [steps]
  (let [get-dependency  (fn [l] (map :dependency (filter #(= l (:then %)) steps)))]
    (zipmap puzzle-letters (map get-dependency puzzle-letters))))


(defn remove-dependency
  [dependencies val]
  (update-all #(remove-value val %) (dissoc dependencies val)))


(defn find-chain
  [steps]
  (loop [chain     ""
         nextSteps (get-dependencies steps)]
    (if (empty? nextSteps)
      chain
      (let [next (first (sort (keys-by-value '() nextSteps)))]
        (recur (str chain next) (remove-dependency nextSteps next))))))

;;;;;;;;;;;;;;
;; Puzzle 2 ;;
;;;;;;;;;;;;;;

(defn remove-dependencies
  [dependencies vals]
  (reduce #(remove-dependency %1 %2) dependencies vals))


(defn find-work [n tasks] (take n (keys-by-value '() tasks)))

(defn work-> [ongoing new] (add-keys ongoing new #(get alphabet-timer %)))


(defn time-chain
  [steps]
  (loop [time            0
         ongoingWork     (work-> {} (find-work 5 (get-dependencies steps)))
         tasks           (remove-keys (get-dependencies steps) (keys ongoingWork))]
    (if (empty? ongoingWork)
      time
      (let [timePassed              (min-val ongoingWork)
            updatedWork             (update-all #(- % timePassed) ongoingWork)
            finishedWork            (keys-by-value 0 updatedWork)
            stillOngoing            (remove-keys updatedWork finishedWork)
            availableTasks          (remove-dependencies tasks finishedWork)
            newTasks                (find-work (- 5 (count stillOngoing)) availableTasks)]
        (recur (+ timePassed time) (work-> stillOngoing newTasks) (remove-keys availableTasks newTasks))))))


(defn -main[]
  (println "Puzzle 1" (find-chain puzzle-input)) ;; EPWCFXKISTZVJHDGNABLQYMORU
  (println "Puzzle 2" (time-chain puzzle-input))) ;; (+ 951 1)
