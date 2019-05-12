(ns marbles_test
    (require [clojure.test :refer :all]
             [marbles :refer :all]))

(deftest test-rotate
  (testing "Rotate vector"
           (is (= (rotate [1 2 3]) [2 3 1]))
           (is (= (rotate 2 [1 2 3]) [3 1 2])) ;; clockwise
           (is (= (rotate -1 [1 2 3]) [3 1 2])) ;; counter-clockwise
           ))

(deftest test-rotate-clockwise
  (testing "Rotate vector"
           (is (= (rotate* 2 [1 2 3]) [3 1 2]))
           (is (= (rotate* -2 [1 2 3]) [2 3 1]))
           (is (= (rotate* -1 [1 2 3]) [3 1 2]))
           ))

(deftest test-rotate-clockwise
  (testing "Rotate vector"
           (is (= (rotate- 2 (transient [1 2 3])) (transient [3 1 2])))
           (is (= (rotate- -2 (transient [1 2 3])) (transient [2 3 1])))
           (is (= (rotate- -1 (transient [1 2 3])) (transient [3 1 2])))
           ))


(deftest test-game-highscore
  (testing "Calculate highscore for game of marbles"
           (is (= (game-highscore 10 23) 32))
          ; (is (= (game-highscore 10 1618) 8317))
           ;(is (= (game-highscore 13 7999) 146373))
          ; (is (= (game-highscore 17 1104) 2764))
           ;(is (= (game-highscore 21 6111) 54718))
           ;(is (= (game-highscore 30 5807) 37305))
           ))
