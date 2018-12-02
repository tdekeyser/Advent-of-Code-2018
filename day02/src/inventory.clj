(ns inventory
    (require [clojure.string :as cljstr]
             [clojure.java.io :as io]))

;;;;;;;;;;;;;;
;; Puzzle 1 ;;
;;;;;;;;;;;;;;

(defn bool->int
  "Returns 1 if true, else 0."
  [bool]
  (if bool 1 0))


(defn occurs-exactly?
  "Returns 1 if any letter appears exactly occ times, else 0."
  [occ message]
  (->> (cljstr/split message #"")
       frequencies
       (map #(last %))
       (some #(= occ %))
       bool->int))


(defn inventory-checksum
  [inventory]
  (loop [twos   0
         threes 0
         inv    inventory]
    (if (empty? inv)
      (* twos threes)
      (let [message (first inv)]
        (recur
          (+ twos (occurs-exactly? 2 message))
          (+ threes (occurs-exactly? 3 message))
          (rest inv))))))

;;;;;;;;;;;;;;
;; Puzzle 2 ;;
;;;;;;;;;;;;;;

(defn differs-1-char?
  "Return true if str1 and str2 differ exactly 1 character at same index, else false."
  [str1 str2]
  (loop [ch1   (cljstr/split str1 #"")
         ch2   (cljstr/split str2 #"")
         diff  0]
    (cond (> diff 1)    false
          (empty? ch1)  (= diff 1)
          :else         (recur
                          (rest ch1)
                          (rest ch2)
                          (if (= (first ch1) (first ch2))
                            diff
                            (inc diff))))))


(defn drop-diff
  "Drop differing chars."
  [str1 str2]
  (->> str1
       (map #(if (= %1 %2) %1 "") str2)
       (apply str)))


(def not-nil? (complement nil?))


(defn find-similar
  [message messageList]
  (loop [list messageList]
    (cond (empty? list)                          nil
          (differs-1-char? message (first list)) (first list)
          :else                                  (recur (rest list)))))


(defn find-similar-for-all
  [messageList1 messageList2]
  (loop [list1 messageList1]
    (let [message        (first list1)
          similar        (find-similar message messageList2)]
      (if (not-nil? similar)
        (drop-diff message similar)
        (recur (rest list1))))))


(def puzzle-input
  (with-open [r (io/reader "input.txt")]
    (doall (line-seq r))))


(defn -main []
  (print "Puzzle 1: Checksum is" (inventory-checksum puzzle-input) "\n")
  (print "Puzzle 2: Similar chars are" (find-similar-for-all puzzle-input puzzle-input) "\n"))
