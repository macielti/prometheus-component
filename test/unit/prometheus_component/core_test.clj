(ns prometheus-component.core-test
  (:require
   [clojure.test :refer :all]
   [iapetos.core :as prometheus]
   [integrant.core :as ig]
   [matcher-combinators.test :refer [match?]]
   [prometheus-component.core :as component.prometheus]
   [schema.test :as s])
  (:import
   (iapetos.registry IapetosRegistry)
   (io.prometheus.client Counter$Child)))

(def config {::component.prometheus/prometheus {:metrics [(prometheus/counter :example/metric)]}})

(s/deftest prometheus-component-test
  (testing "That we can define prometheus component and start it"
    (let [system (ig/init config)]
      (is (match? {::component.prometheus/prometheus {:registry #(= (type %) IapetosRegistry)}}
                  system))

      (is (match? #(= (type %) Counter$Child)
                  (-> system ::component.prometheus/prometheus :registry :example/metric))))))
