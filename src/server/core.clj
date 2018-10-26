
(ns server.core
	(:require
		[clojure.string :as _clojure_string]
		[clojure.spec.alpha :as _closure_spec]
		[ring.util.request :as ring_util_request]
		[compojure.core :refer :all]
		[org.httpkit.server :refer [run-server]])
	(:gen-class))


(_closure_spec/def ::data )

(def data {
	"name" "Sample Program"
	"elements" [
		"First"
		"Second"
		"Third"
	]
})

(def port 8080)

(defn onGetName [req]
	(get data "name")
)

(defn onGetElements [req]
	(_clojure_string/join ", " (get data "elements"))
)

(defn onGetRandomElement [req]
	(get (get data "elements") (rand-int 3))
)

(defn onPost [req]
	{:status 200
	 :headers {"Content-Type" "application/json"}
	 :body (ring_util_request/body-string req)}
)

;; Here we define server handlers

(defroutes server
	(GET "/" [] "Hello World!")
	(POST "/" [] onPost)
	(GET "/name" [] onGetName)
	(GET "/elements" [] onGetElements)
	(GET "/elements/random" [] onGetRandomElement)
)

(defn -main [& args]
	(run-server server {:port port})
	(println "Server is running. Listening in port " port)
)
