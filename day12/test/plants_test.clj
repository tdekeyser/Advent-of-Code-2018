(ns plants_test
    (require [clojure.test :refer :all]
             [plants :refer :all]))

(deftest test-apply-rule
  (let [rules (get-rules (get-puzzle-input "input.txt"))]
    (testing "Find and apply rule"
             (is (= (apply-rules rules "..##.") "."))
             (is (= (apply-rules rules "###..") "."))
             (is (= (apply-rules rules "#....") "."))
             (is (= (apply-rules rules "#####") "#")))))


(deftest test-evolve
  (let [puzzle-input  (get-puzzle-input "test-input.txt")
        initial-state (get-initial-state puzzle-input)
        rules         (get-rules puzzle-input)
        actual        (first (evolve [initial-state rules]))]
    (testing "Proceed to next state"
             (is (= (:start actual) -5))
             (is (= (:content actual) ".....#...#....#.....#..#..#..#.....")))
    (testing "Count state"
             (is (= (count-state (evolve-n 20 [initial-state rules])) 325)))))
