(ns chronal_test
    (require [clojure.test :refer :all]
             [chronal :refer :all]))

(def aoc-test-area
  {:1 [1 1] :2 [1 6] :3 [8 3] :4 [3 4] :5 [5 5] :6 [8 9]})

(def some-coords
  {:1 [1 6] :2 [8 3] :3 [3 4] :4 [5 5] :5 [8 9]})

(def some-border
  {:x [1 8] :y [3 9]})


(deftest test-chronal-area
  (testing "Calculate the greatest area between coordinates that is finite."
           (is (= (chronal-area aoc-test-area) [:5 17]))))


(deftest test-chronal-safe
  (testing "Count the size of the safe area -- points not further than x from all coords"
           (is (= (chronal-safe 32 aoc-test-area) 16))))


(deftest test-closest
  (testing "Get closest coordinate"
           (is (= (closest-by-index some-coords [3 0]) :3))
           (is (= (closest-by-index some-coords [0 0]) nil))
           (is (= (closest-by-index {:1 [1 6] :2 [8 3] :3 [3 4] :4 [1 1] :5 [8 9]} [0 0]) :4))
           (is (= (closest-by-index {:1 [1 6] :2 [8 3] :3 [3 4] :4 [1 1] :5 [8 9]} [9 9]) :5))
           (is (= (closest-by-index {:1 [1 6] :2 [8 3] :3 [3 4] :4 [1 1] :5 [8 9]} [8 3]) :2))))


(deftest test-define-border
  (testing "Get the borders of the area comprised by coordinates"
           (is (= (->border some-coords) some-border))))


(deftest test-on-border?
  (testing "Check if coord is on border"
           (is (true? (on-border? some-border [8 4])))
           (is (true? (on-border? some-border [1 5])))
           (is (true? (on-border? some-border [4 9])))
           (is (nil? (on-border? some-border [4 7])))
           (is (nil? (on-border? some-border [0 0])))))

