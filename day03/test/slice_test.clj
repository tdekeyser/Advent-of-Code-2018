(ns slice_test
    (require [clojure.test :refer :all]
             [slice :refer :all]))

(deftest test-parse
  (testing "Parse one entry claim."
           (is (= (parse "#7 @ 507,506: 22x10") {:x 507 :y 506 :w 22 :h 10}))
           (is (= (parse "#1 @ 16,576: 17x14") {:x 16 :y 576 :w 17 :h 14}))
           (is (= (parse "#230 @ 1,0: 340x4444") {:x 1 :y 0 :w 340 :h 4444}))))

(deftest test-contains?
  (testing "Return true if coordinate is in dimension."
           (let [entry {:x 1 :y 3 :w 4 :h 4}]
             (is (true? (contained-in? 1 3 entry)))
             (is (true? (contained-in? 2 3 entry)))
             (is (true? (contained-in? 3 3 entry)))
             (is (true? (contained-in? 3 6 entry)))
             (is (false? (contained-in? 0 0 entry)))
             (is (false? (contained-in? 1 0 entry))))))

(deftest test-overlap?
  (testing "Return true if square with dim x and y is overlapped by >= 2 entries."
           (let [entries '({:x 1 :y 3 :w 4 :h 4}
                           {:x 3 :y 1 :w 4 :h 4}
                           {:x 5 :y 5 :w 2 :h 2})]
             (is (true? (overlap? 3 3 entries)))
             (is (true? (overlap? 3 4 entries)))
             (is (true? (overlap? 4 3 entries)))
             (is (true? (overlap? 4 4 entries)))
             (is (false? (overlap? 0 0 entries)))
             (is (false? (overlap? 1 0 entries)))
             (is (false? (overlap? 2 2 entries)))
             (is (false? (overlap? 2 3 entries)))
             (is (false? (overlap? 5 6 entries))))))

(deftest test-count-overlap
  (testing "Count overlapping squares"
           (is (= (count-overlap 10 '({:x 1 :y 3 :w 4 :h 4}
                                      {:x 3 :y 1 :w 4 :h 4}
                                      {:x 5 :y 5 :w 2 :h 2}))
                  4))))


(deftest test-generate-coords
  (testing "Get all coordinates based on dimensions"
           (is (= (generate-coords {:x 0 :y 0 :w 2 :h 2}) [[0 0] [0 1] [1 0] [1 1]]))
           (is (= (generate-coords {:x 0 :y 0 :w 3 :h 2}) [[0 0] [0 1] [1 0] [1 1] [2 0] [2 1]]))))


(deftest test-find-non-overlap
  (testing "Find non-overlapping element"
           (is (= (find-non-overlap '({:x 1 :y 3 :w 4 :h 4}
                                      {:x 3 :y 1 :w 4 :h 4}
                                      {:x 5 :y 5 :w 2 :h 2}
                                      {:x 2 :y 2 :w 3 :h 3}))
                  2))))
