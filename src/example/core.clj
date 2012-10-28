(ns example.core
  (:use compojure.core)
  (:require [compojure.route :as route]
            [hiccup.core :as hiccup]
            [hiccup.form :as form]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :as reload]
            [ring.middleware.session :as session]
            [ring.middleware.params :as params]
            [ring.util.response :as response]))

(defn guess-page [req]
  (hiccup/html
      (form/form-to
       [:post "/guess"]
       [:fieldset
        [:legend "Take a guess:"]
        (let [old-number (get-in req [:params "old-number"])
              missing-number-error (= (get-in req [:params "error"]) "missing-number")]
          [:div (when missing-number-error
                  {:style "background-color: red"})
           (form/label :number "Number:")
           [:div.controls
            (form/text-field :number old-number)
            (when old-number
              [:span.message "The guess is wrong"])
            (when missing-number-error
              [:span.error-message "A number is required"])]])
        (form/submit-button "Submit")])))

(defn handle-guess-page [req]
  (let [number-str (get-in req [:params "number"])]
    (if-let [number (try (Integer/parseInt number-str)
                         (catch Exception e
                           nil))]
      (if (= number 42)
        (response/redirect "/success")
        (response/redirect (str "/guess?old-number=" number)))
      (response/redirect (str "/guess?error=missing-number")))))

(defn app-routes [req]
  (routing req
           (GET "/index" [] "Hello, world!")
           (GET "/guess" [] guess-page)
           (POST "/guess" [] handle-guess-page)
           (GET "/success" [] "Success!")
           (route/not-found "Page not found")))

(def ring-app
     (-> app-routes
         params/wrap-params
         (reload/wrap-reload ['example.core])))

(defn -main
  [& args]
  (jetty/run-jetty #'ring-app {:port 8888 :join? false}))
