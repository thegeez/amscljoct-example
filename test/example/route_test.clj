(ns example.route-test
  (:use example.core
        clojure.test
        peridot.core))

(deftest not-found-route
  (-> (session ring-app)
      (request "/this-page-does-not-exist")))
