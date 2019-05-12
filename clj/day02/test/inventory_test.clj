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

(deftest test-differs-1-char?
  (testing "Check if a boxId is the correct one (has another that differs exactly 1 character)."
           (is (= (differs-1-char? "abc" "abd") true))
           (is (= (differs-1-char? "abc" "abc") false))
           (is (= (differs-1-char? "abc" "ayc") true))
           (is (= (differs-1-char? "abc" "rbc") true))
           (is (= (differs-1-char? "abc" "bca") false))
           (is (= (differs-1-char? "abc" "bac") false))
           (is (= (differs-1-char? "acb" "abc") false))
           (is (= (differs-1-char? "abc" "abg") true))
           (is (= (differs-1-char? "abc" "atd") false))
           (is (= (differs-1-char? "abcpoppie" "tbcpoppie") true))))

(deftest test-find-similar
  (testing "Finds similar string to input"
           (is (= (find-similar "abc" '("bdhe" "tup" "acc")) "acc"))
           (is (= (find-similar "abc" '("bdhe" "tup")) nil))
           (is (= (find-similar "abcd" '("bdhe" "tup" "acc" "abce" "iope")) "abce"))))

(deftest test-drop-diff
  (testing "Drop differing chars."
           (is (= (drop-diff "abc" "adc") "ac"))
           (is (= (drop-diff "patoe" "patie") "pate"))))

(deftest test-find-similar-for-all
  (testing "Puzzle 2"
           (is (= (find-similar-for-all '("bdhe" "tup" "acc") '("bdhe" "tup" "acd")) "ac"))
           (is (= (find-similar-for-all '("bdhe" "tup" "acc" "abce" "iope") '("iope" "bdhe" "tup" "acc" "pbce")) "bce"))))
