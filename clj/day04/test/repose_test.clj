(ns repose_test
    (require [clojure.test :refer :all]
             [repose :refer :all]))

(deftest test-parse
  (testing "Parse log data"
           (is (= (parse "[1518-04-15 23:58] Guard #373 begins shift") "151804152358 Guard #373 begins shift"))
           (is (= (parse "[1518-01-20 00:57] wakes up") "151801200057 wakes up"))))

(deftest test-count-sleeptime
  (testing "Count total amount of sleep time per guard"
           (is
             (=
               (count-sleeptime
                 '("151801122357 Guard #3209 begins shift"
                   "151801130013 falls asleep"
                   "151801130017 wakes up"))
               [{:id "3209" :sleeptime 4 :minutes [13 14 15 16]}]))
           (is
             (=
               (count-sleeptime
                 '("151801122357 Guard #3209 begins shift"
                   "151801130013 falls asleep"
                   "151801130015 wakes up"
                   "151801132356 Guard #751 begins shift"
                   "151801140054 falls asleep"
                   "151801140057 wakes up"))
               [{:id "3209" :sleeptime 2 :minutes [13 14]}
                {:id "751" :sleeptime 3 :minutes [54 55 56]}]))
           (is
             (=
               (count-sleeptime
                 '("151801122357 Guard #3209 begins shift"
                   "151801130013 falls asleep"
                   "151801130015 wakes up"
                   "151801132356 Guard #751 begins shift"
                   "151801140054 falls asleep"
                   "151801140057 wakes up"
                   "151801122357 Guard #3209 begins shift"
                   "151801130014 falls asleep"
                   "151801130016 wakes up"))
               [{:id "3209" :sleeptime 4 :minutes [13 14 14 15]}
                {:id "751" :sleeptime 3 :minutes [54 55 56]}]))
           ))


(deftest test-max-sleepminute
  (testing "Get minute that is a guard is maximally asleep"
           (is (= (max-sleepminute [1 2 3 4 1 1 1]) 1))
           (is (= (max-sleepminute [1 2 3 3 1 9 2]) 3))
           (is (= (max-sleepminute [1 2 3 4 2 1 2]) 2))))

(deftest test-most-lazy-guard
  (testing "Get lazy guard id and most slept minutes"
           (is (= (most-lazy-guard '("151801122357 Guard #3209 begins shift"
                                     "151801130013 falls asleep"
                                     "151801130015 wakes up"
                                     "151801132356 Guard #751 begins shift"
                                     "151801140054 falls asleep"
                                     "151801140055 wakes up"
                                     "151801122357 Guard #3209 begins shift"
                                     "151801130014 falls asleep"
                                     "151801130016 wakes up"
                                     "151901132356 Guard #751 begins shift"
                                     "151901140054 falls asleep"
                                     "151901140055 wakes up"
                                     "152001132356 Guard #751 begins shift"
                                     "152001140054 falls asleep"
                                     "152001140055 wakes up"))
                  ["3209" 14]))))

(deftest test-most-lazy-guard-on-minute
  (testing "Get lazy guard id and most slept minutes"
           (is (= (most-lazy-guard-on-minute '("151801122357 Guard #3209 begins shift"
                                               "151801130013 falls asleep"
                                               "151801130015 wakes up"
                                               "151801132356 Guard #751 begins shift"
                                               "151801140054 falls asleep"
                                               "151801140055 wakes up"
                                               "151801122357 Guard #3209 begins shift"
                                               "151801130014 falls asleep"
                                               "151801130016 wakes up"
                                               "151901132356 Guard #751 begins shift"
                                               "151901140054 falls asleep"
                                               "151901140055 wakes up"
                                               "152001132356 Guard #751 begins shift"
                                               "152001140054 falls asleep"
                                               "152001140055 wakes up"))
                  ["751" 54]))))
