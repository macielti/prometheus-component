(ns prometheus-component-test
  (:require [clojure.string :as str]
            [clojure.test :refer :all]
            [iapetos.core :as prometheus]
            [integrant.core :as ig]
            [prometheus-component.core :as component.prometheus]
            [common-clj.integrant-components.routes :as component.routes]
            [service-component.core :as component.service]
            [matcher-combinators.test :refer [match?]]
            [io.pedestal.test :as test]
            [schema.test :as s]))

(def routes [["/metrics" :get [component.prometheus/expose-metrics-http-request-handler]
              :route-name :fetch-metrics]])

(def config
  {::component.prometheus/prometheus {:metrics [(prometheus/counter :example/metric)]}
   ::component.routes/routes         {:routes routes}
   ::component.service/service       {:components {:config     {:service          {:host "0.0.0.0"
                                                                                   :port 8080}
                                                                :prometheus-token "test-token"}
                                                   :prometheus (ig/ref ::component.prometheus/prometheus)
                                                   :routes     (ig/ref ::component.routes/routes)}}})

(s/deftest prometheus-component-test
  (let [system (ig/init config)
        service-fn (-> system ::component.service/service :io.pedestal.http/service-fn)
        prometheus (::component.prometheus/prometheus system)]

    (testing "That we can fetch metrics"
      (is (match? {:status 200
                   :body   #(str/includes? % "HELP example_metric_total a counter metric.")}
                  (test/response-for service-fn :get "/metrics" :headers {"authorization" "Bearer test-token"}))))

    (testing "That we can't fetch metrics without authorization"
      (is (match? {:status 403
                   :body   "Not Authorized"}
                  (test/response-for service-fn :get "/metrics" :headers {"authorization" "Bearer wrong-token"}))))

    (testing "That we can produce metric count incrementation"

      (prometheus/inc (:registry prometheus) :example/metric)

      (is (match? {:status 200
                   :body   #(str/includes? % "example_metric_total 1")}
                  (test/response-for service-fn :get "/metrics" :headers {"authorization" "Bearer test-token"})))

      (prometheus/inc (:registry prometheus) :example/metric)

      (is (match? {:status 200
                   :body   #(str/includes? % "example_metric_total 2")}
                  (test/response-for service-fn :get "/metrics" :headers {"authorization" "Bearer test-token"}))))

    (ig/halt! system)))
