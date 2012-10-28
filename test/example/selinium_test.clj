(ns example.selinium-test
  (:use example.core
        clojure.test)
  (:require [clj-webdriver.core :as w]))

(use-fixtures :each
              (fn [f] (let [server (-main)]
                        (f)
                        (.stop server))))

(deftest basic-test
  (is (slurp "http://localhost:8888/index")))

(deftest validation-fail
  (let [driver (w/new-driver :firefox)]
    (w/to driver "http://localhost:8888/guess")
    (w/input-text (w/find-element driver {:id "number"}) "3")
    (w/click (w/find-element driver {:type "submit"}))
    (is (= (w/text (w/find-element driver {:class "message"}))
           "The guess is wrong"))
    (w/quit driver)
    ))
