(ns memory_test
    (require [clojure.test :refer :all]
             [memory :refer :all]))


(deftest test-count-metadata
  (testing "Counting metadata"
           (is (= (count-metadata '(0 1 99)) [99 []]))
           (is (= (count-metadata '(0 1 99 2)) [99 [2]]))
           (is (= (count-metadata '(1 1 0 1 99 2)) [101 []]))
           (is (= (count-metadata '(2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2)) [138 []]))))


(deftest test-count-value
  (testing "Count value of nodes"
           (is (= (count-value '(0 1 99)) [99 []]))
           (is (= (count-value '(0 3 10 11 12)) [33 []]))
           (is (= (count-value '(1 1 0 1 99 2)) [0 []]))
           (is (= (count-value '(2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2)) [66 []]))))
