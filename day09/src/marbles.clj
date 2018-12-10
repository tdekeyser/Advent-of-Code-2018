(ns marbles)

(defn rotate
  "Not efficient but pretty cool"
  ([coll] (conj (into [] (rest coll)) (first coll)))
  ([n coll] (nth (iterate rotate coll) (mod n (count coll)))))


(defn rotate*
  [n coll]
  (let [n* (mod n (count coll))] (concat (drop n* coll) (take n* coll))))


(defn game-highscore
  [players last-marble]
  (loop [marble 1
         game   [0]
         scores (into [] (repeat players 0))]
    (cond (= marble (inc last-marble))  (apply max scores)
          (= (mod marble 23) 0)         (let [rotated (rotate* -7 game)]
                                          (do (println marble) (flush)
                                            (recur
                                              (inc marble)
                                              (rest rotated)
                                              (update scores (mod marble players) #(+ marble (+ (first rotated) %))))))
          :else                         (recur
                                          (inc marble)
                                          (into [marble] (rotate* 2 game))
                                          scores))))


(defn -main []
  (println "Puzzle 1" (game-highscore 416 71617)) ;; 436720
  (println "Puzzle 2" (game-highscore 416 (* 71617 100))))
