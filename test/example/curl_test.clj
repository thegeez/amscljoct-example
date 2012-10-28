(ns example.curl-test
  (:use example.core
        clojure.test))

(deftest post-submit-test-fail
  (testing "A http request as Clojure code"
    (let [request {:scheme :http
                   :request-method :post
                   :content-type "application/x-www-form-urlencoded"
                   :uri "/guess"
                   :server-name "localhost"
                   :params {"number" "3"}
                   :headers {"user-agent" "Mozilla/5.0 Firefox/11.0"}
                   :content-length 8
                   :server-port 8888}
          response {:status 302
                    :headers {"Location" "/guess?old-number=3"}
                    :body ""}]
      (is (= (ring-app request)
             response)))))

