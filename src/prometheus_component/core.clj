(ns prometheus-component.core
  (:require [clojure.tools.logging :as log]
            [iapetos.core :as prometheus]
            [iapetos.export :as export]
            [integrant.core :as ig]
            [schema.core :as s]))

(defmacro report-elapsed-time!
  "Measures the elapsed time (msecs) to run the given body of code, reports it as a prometheus metric, and returns the result of the body."
  [registry id & body]
  `(let [start# (. System (nanoTime))
         result# (do ~@body)
         end# (. System (nanoTime))]
     (prometheus/observe ~registry :elapsed-time {:id ~id} (/ (double (- end# start#)) 1000000.0))
     result#))

(s/defn expose-metrics-http-request-handler
  [{headers                     :headers
    {:keys [prometheus config]} :components}]
  (if (= (get headers "authorization") (str "Bearer " (:prometheus-token config)))
    {:status 200
     :body   (export/text-format (:registry prometheus))}
    {:status 403
     :body   "Not Authorized"}))

(def default-metrics
  [(prometheus/counter :http-request-response {:labels [:status :service :endpoint]})
   (prometheus/summary :http-request-response-timing {:labels    [:service :endpoint]
                                                      :quantiles {0.5 0.05, 0.9 0.1, 0.99 0.001}})
   (prometheus/summary :http-request-in-handle-timing-v2 {:labels    [:service :endpoint]
                                                          :quantiles {0.5 0.05, 0.9 0.1, 0.99 0.001}})
   (prometheus/summary :job-execution-timing {:labels    [:service :job-id]
                                              :quantiles {0.5 0.05, 0.9 0.1, 0.99 0.001}})
   (prometheus/summary :elapsed-time {:labels    [:id]
                                      :quantiles {0.5 0.05, 0.9 0.1, 0.99 0.001}})
   (prometheus/gauge :percentage-used-memory-host {:labels [:service :host]})])

(defmethod ig/init-key ::prometheus
  [_ {:keys [metrics]}]
  (log/info :starting ::prometheus)
  {:registry (-> (partial prometheus/register (prometheus/collector-registry))
                 (apply (concat metrics default-metrics)))})

(defmethod ig/halt-key! ::prometheus
  [_ _]
  (log/info :stopping ::prometheus))
