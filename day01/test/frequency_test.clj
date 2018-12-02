(ns frequency_test
    (:require [clojure.test :refer :all]
              [frequency :refer :all]))


(deftest test-add-frequency
  (testing "Add frequency sequence"
           (is (= (add-frequency '(1 1 1)) 3))
           (is (= (add-frequency '(1 1 -2)) 0))
           (is (= (add-frequency '(-1 -2 -3)) -6))))


(deftest test-first-double-frequency
  (testing "Find the first frequency that appears twice"
           (is (= (first-double-frequency '(1 -1)) 0))
           (is (= (first-double-frequency '(3 3 4 -2 -4)) 10))
           (is (= (first-double-frequency '(-6 3 8 5 -6)) 5))
           (is (= (first-double-frequency '(7 7 -2 -7 -4)) 14))))
