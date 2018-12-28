(ns classification_test
    (require [clojure.test :refer :all]
             [classification :refer :all]))

(deftest test-addr
  (is (= (addr [2 0 1 1] [0 0 2 0]) [3 0 1 1]))
  (is (= (addr [2 0 1 1] [0 0 0 0]) [4 0 1 1])))

(deftest test-addi
  (is (= (addi [2 0 1 1] [0 0 2 0]) [4 0 1 1]))
  (is (= (addi [2 0 1 1] [0 0 0 0]) [2 0 1 1])))

(deftest test-mulr
  (is (= (mulr [2 0 1 1] [0 0 2 0]) [2 0 1 1]))
  (is (= (mulr [2 0 1 1] [0 0 0 0]) [4 0 1 1])))

(deftest test-muli
  (is (= (muli [2 0 1 1] [0 0 2 0]) [4 0 1 1]))
  (is (= (muli [2 0 1 1] [0 0 0 0]) [0 0 1 1])))

(deftest test-setr
  (is (= (setr [2 0 1 1] [0 1 2 0]) [0 0 1 1])))

(deftest test-seti
  (is (= (seti [2 0 1 1] [0 5 2 0]) [5 0 1 1])))

(deftest test-gtri
  (is (= (gtri [2 0 1 1] [0 0 2 0]) [1 0 1 1]))
  (is (= (gtri [1 0 2 1] [0 0 2 0]) [0 0 2 1])))

(deftest test-eqrr
  (is (= (eqrr [2 0 1 1] [0 0 2 0]) [0 0 1 1]))
  (is (= (eqrr [2 0 1 1] [0 0 0 0]) [1 0 1 1])))
