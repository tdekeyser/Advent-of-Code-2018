(ns alchemy_test
    (require [clojure.test :refer :all]
             [alchemy :refer :all]))

(deftest test-interact?
  (testing "Check whether two units interact"
           (is (true? (interact? \a \A)))
           (is (true? (interact? \A \a)))
           (is (true? (interact? \D \d)))
           (is (true? (interact? \b \B)))
           (is (false? (interact? \b \b)))
           (is (false? (interact? \b \a)))
           (is (false? (interact? \Q \a)))))

(deftest test-react-once
  (testing "One reaction"
           (is (= (react-once "aA") ""))
           (is (= (react-once "abBA") "aA"))
           (is (= (react-once "abAB") "abAB"))
           (is (= (react-once "aabAAB") "aabAAB"))))

(deftest test-react
  (testing "Reaction against polymer - perform and count"
           (is (= (react "aA") ""))
           (is (= (react "abBA") ""))
           (is (= (react "abAB") "abAB"))
           (is (= (react "aabAAB") "aabAAB"))))

(deftest test-react*
  (testing "Remove unit from polymer"
           (is (= (react* "dabAcCaCBAcCcaDA") 4))
           (is (= (react* "aDAaDAaDAaDAaDA") 0))
           (is (= (react* "dabAcCaCBAaDA") 4))
           (is (= (react* "SatRrgttt") 3))))