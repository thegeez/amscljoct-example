(defproject net.thegeez/octamsclj-example "0.0.1"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [ring/ring-core "1.1.1"]
                 [ring/ring-jetty-adapter "1.1.1"]
                 [ring/ring-devel "1.1.1"]
                 [compojure "1.1.1"]
                 [hiccup "1.0.0"]]
  :profiles {:dev {:dependencies [[kerodon "0.0.6"]
                                  [clj-webdriver "0.6.0-beta1"]
                                  ]}})
