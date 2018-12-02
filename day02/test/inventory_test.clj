(ns inventory_test
    (:require [clojure.test :refer :all]
              [inventory :refer :all]))

(deftest test-occurs-exactly?
  (testing "Returns true if any letter appears exactly occ times, else false."
           (is (= (occurs-exactly? 2 "abcdef") 0))
           (is (= (occurs-exactly? 3 "abcdef") 0))
           (is (= (occurs-exactly? 2 "bababc") 1))
           (is (= (occurs-exactly? 3 "bababc") 1))
           (is (= (occurs-exactly? 2 "aabcdd") 1))
           (is (= (occurs-exactly? 3 "aabcdd") 0))
           (is (= (occurs-exactly? 2 "ababab") 0))
           (is (= (occurs-exactly? 3 "ababab") 1))))

(deftest test-inventory-checksum
  (testing "Test checksum creation"
           (is (= (inventory-checksum '("bababc"))) (* 1 1))
           (is (= (inventory-checksum '("abcdef" "abcdef"))) (* 0 0))
           (is (= (inventory-checksum '("abcdef" "bababc"))) (* 1 1))
           (is (= (inventory-checksum '("aabcdd" "bababc"))) (* 2 1))
           (is (= (inventory-checksum '("aabcdd" "bababc" "ababab"))) (* 2 2))
           (is
             (= (inventory-checksum '("abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"))
                (* 4 3)))))
