(ns chain_test
    (require [clojure.test :refer :all]
             [chain :refer :all]))


(deftest test-chain
  (testing "Chain steps together"
           (is (= (chain (puzzle-input "test-input.txt")) "CABDFE"))))

