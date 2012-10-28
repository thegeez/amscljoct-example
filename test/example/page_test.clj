(ns example.page-test
  (:use example.core
        clojure.test
        kerodon.core
        kerodon.test))

(deftest not-found-route
  (-> (session ring-app)
      (visit "/this-page-does-not-exist")
      (has (status? 404))))

(deftest index-route
  (-> (session ring-app)
      (visit "/index")
      (has (text? "Hello, world!"))))

(deftest index-route-nested
  (has (visit (session ring-app)
              "/index")
       (text? "Hello, world!")))

(deftest index-route-let
  (let [session-begin (session ring-app)
        session-visit (visit session-begin "/index")]
    (has session-visit (text? "Hello, world!"))))

(deftest index-route-is
  (-> (session ring-app)
      (visit "/index")
      ;;(has (text? "Hello, world!"))
      ((fn [session]
         (is (= (get-in session [:response :body]) "Hello, world!"))))
      ))

(deftest map-access
  (let [body "Hello, world!"
        session (-> {}
                    (assoc :response {:status 200, :body body})
                    (assoc :request {:uri "/index"}))]
    (is (= body
           (:body (:response session))
           (get-in session [:response :body])))))

;; get page
(deftest get-page-route
  (testing "Page shows a guess box"
    (-> (session ring-app)
        (visit "/guess")
        (within [:legend]
                (has (text? "Take a guess:"))))))

;; validation fail
(deftest validation-fail
  (testing "Submitting without content in input should show error message"
    (-> (session ring-app)
        (visit "/guess")
        (press "Submit")

        (follow-redirect)
        (within [:span.error-message]
                (has (text? "A number is required"))))))

(deftest failure-page
  (testing "Submitting a wrong answer show a message"
    (-> (session ring-app)
        (visit "/guess")
        (fill-in "Number:" "3")
        (press "Submit")

        (follow-redirect)
        (within [:span.message]
                (has (text? "The guess is wrong"))))))

(deftest failure-page-inspect
  (testing "Submitting a wrong answer show a message"
    (-> (session ring-app)
        (visit "/guess")
        (fill-in "Number:" "3")
        (press "Submit")

        (follow-redirect)
        ((fn [session]
           (println "session: " (pr-str session))
           session))
        (within [:span.message]
                (has (text? "The guess is wrong"))))))

;; {:response {:status 200, :headers {"Content-Type" "..."},
;;             :body "<form action=\"/guess\" method=\"POST\">...</form>"},
;;  :enlive ({:tag :html, :attrs nil, :content ("...")}),
;;  :request {:server-port 80,
;;            :server-name "localhost",
;;            :uri "/guess",
;;            :query-string "old-number=3",
;;            :scheme :http,
;;            :request-method :get,
;;            :headers {"referrer" "http://localhost/guess"}},
;;  :app #<reload$wrap_reload$fn__1412@16df0d6>}

(deftest success-page
  (testing "Submitting the right answer gets you to /success"
    (-> (session ring-app)
        (visit "/guess")
        (fill-in "Number:" "42")
        (press "Submit")

        (follow-redirect)
        (has (text? "Success!")))))
