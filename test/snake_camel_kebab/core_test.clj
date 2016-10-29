(ns snake-camel-kebab.core-test
  (:require [clojure.test :refer :all]
            [snake-camel-kebab.core :refer :all]))



(deftest snake-test
  (testing "Snake failed"
    (is (= 0 (compare ["this" "is" "another" "test"] (tokenize "this-is-another-test"))))))

(deftest camel-test
  (testing "Camel failed"
    (is (= 0 (compare ["this" "Is" "Another" "Test"] (tokenize "thisIsAnotherTest"))))))

(deftest kebab-test
  (testing "Kebab failed"
    (is (= 0 (compare ["this" "is" "another" "test"] (tokenize "this_is_another_test"))))))
