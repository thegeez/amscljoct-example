(ns example.core-test
  (:use clojure.test))

(deftest a-test-case
  (testing "Arithmetic should work"
    (is (= (/ 6
              (* 2
                 (+ 2 1)))
           1))
    (is (= (* (/ 6 2)
              (+ 2 1))
           9))))

;; f(a,b) = a + b 
(def plus (fn [a b]
            (+ a b)))

(defn plus [a b]
  (+ a b))

(plus 3 4)
;; => 7

