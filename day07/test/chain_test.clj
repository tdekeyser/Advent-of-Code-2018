(ns chain_test
    (require [clojure.test :refer :all]
             [chain :refer :all]))


(deftest test-chain
  (testing "Chain steps together"
           (is (= (find-chain puzzle-input) "EPWCFXKISTZVJHDGNABLQYMORU"))))

