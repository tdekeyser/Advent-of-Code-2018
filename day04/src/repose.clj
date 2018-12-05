(ns repose
    (require [clojure.java.io :as io]
             [clojure.string :as cljstr]))

(defn guard-id
  [entry]
  (last (re-find #"#(\d+) " entry)))

(defn timestamp
  [entry]
  (Long. (first (cljstr/split entry #" "))))

;;;;;;;;;;;;;;;;;;;
;; Input parsing ;;
;;;;;;;;;;;;;;;;;;;

(defn parse
  [entry]
  (->> entry (re-find #"^[\[](\d+)-(\d+)-(\d+) (\d+):(\d+)[\]]( .+)$") rest (apply str)))


(defn sort-by-timestamp
  [data]
  (sort-by #(Long. (first (cljstr/split % #" "))) data))


(def puzzle-input
  (with-open [r (io/reader "input.txt")]
    (->> r line-seq (map parse) sort-by-timestamp doall)))

;;;;;;;;;;;;;;
;; Puzzle 1 ;;
;;;;;;;;;;;;;;

(defn update-log
  [log guardId asleep wakeUp]
  (let [newLog (first (filter #(= guardId (:id %)) log))]
    (if (empty? newLog)
      (conj log {:id guardId :sleeptime (- wakeUp asleep) :minutes (range asleep wakeUp)})
      (-> newLog
          (update :sleeptime + (- wakeUp asleep))
          (update :minutes concat (range asleep wakeUp))
          (cons (filter #(not (= guardId (:id %))) log))))))


(defn count-sleeptime
  [data]
  (loop [entries   data
         guardId   nil
         asleep    0
         log       []]
    (let [entry (first entries)]
      (cond (empty? entries)                  log
            (.contains entry "Guard #")       (recur (rest entries) (guard-id entry) 0 log)
            (.contains entry "falls asleep")  (recur (rest entries) guardId (mod (timestamp entry) 100) log)
            (.contains entry "wakes up")      (recur (rest entries) guardId 0 (update-log log guardId asleep (mod (timestamp entry) 100)))
            :else                             (throw (Exception. (str "Cannot parse" entry)))))))


(defn max-sleepminute
  [minutes]
  (->> minutes frequencies (sort-by val) reverse first first))


(defn most-lazy-guard
  [data]
  (as-> (count-sleeptime data) $
        (sort-by :sleeptime $)
        (reverse $)
        (first $)
        (vector (:id $) (max-sleepminute (:minutes $)))))


(defn -main []
  (println "Puzzle 1: Slept most [guardID minute]" (most-lazy-guard puzzle-input)))
