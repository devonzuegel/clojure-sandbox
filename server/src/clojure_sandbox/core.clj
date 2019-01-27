(ns clojure-sandbox.core
 (:require
    [ring.adapter.jetty :as jetty]
    [compojure.core :as compojure]
    [compojure.route :as route]))


(+ 1 3)

(println "Hello Sebas")

; TODO: why are you silent?
; (throw (ex-info "becuase u suck" {}))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

; define constant
(def pi 3.14)

(def hi 123123)
(def hello 222)

; semantically the same, but diff operations
(second [1 2 3])
(nth [1 2 3] 1)
(nth '(1 2 3) 1) ; this is slow
'(1 2 3)

{:lieb "ling"} ; immutable!
(def mymap1 {:lieb "ling"})
(def mymap2 (assoc mymap1 :chiqui "yo"))
mymap1

; sets
#{1 2 3}
; #{1 2 3 3}


;
; HTTP stuff
;

; def defines globals

; has implicit return
(defn handler [request]
  ; local variable
  (let [ip (get request :ip)]
    {:status 200
     :body ip}))


; equivalent to the above ^
(def handler
  (fn [request]))
    ; do stuff

(defn yell-when-nothing [] "YOU CAN'T DO THAT\n")

(def myhandler
  (compojure/routes ; composes the routes
   (compojure/GET "/foo" request
     "this is the response\n")
   (route/not-found (yell-when-nothing)))

; {:status 200
;       :body "Sorry that was a joke\n"
;       :headers {}}

  "A very simple web server using Ring & Jetty"
  []
  (def server
    (jetty/run-jetty
     #'myhandler ; don't pass just the object fn but rather than name, resolve it each time... allows us to redefine it
     {:port (Integer. 3000) :join? false})))
