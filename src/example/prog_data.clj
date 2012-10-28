(ns example.prog-data)

(def v [1 2 3])

(conj v 4)
;; => [1 2 3 4]

(def m {:a 2})

(assoc m :b v)
;; => {:a 2
;;     :b [1 2 3 4]}

(get v 2)
;; => 3

(v 2)
;; => 3

(get m :a)
;; => 2

(get-in (assoc m :b v) [:b 2])
;; => 3


