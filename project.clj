(defproject net.clojars.macielti/prometheus-component "0.2.1"

  :description "Prometheus component for integrant"

  :url "https://github.com/macielti/prometheus-component"

  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.12.0"]
                 [org.clojure/tools.logging "1.3.0"]
                 [clj-commons/iapetos "0.1.14"]
                 [prismatic/schema "1.4.1"]
                 [integrant "0.13.1"]]

  :profiles {:dev {:resource-paths ^:replace ["test/resources"]

                   :test-paths     ^:replace ["test/unit" "test/integration" "test/helpers"]

                   :plugins        [[lein-cloverage "1.2.4"]
                                    [com.github.clojure-lsp/lein-clojure-lsp "1.4.16"]
                                    [com.github.liquidz/antq "RELEASE"]]

                   :dependencies   [[net.clojars.macielti/common-test-clj "5.2.3"]
                                    [net.clojars.macielti/service-component "2.4.2"]
                                    [net.clojars.macielti/common-clj "43.74.74"]
                                    [org.slf4j/slf4j-api "2.0.16"]
                                    [ch.qos.logback/logback-classic "1.5.16"]
                                    [nubank/matcher-combinators "3.9.1"]
                                    [io.pedestal/pedestal.jetty "0.7.2"]
                                    [io.pedestal/pedestal.service "0.7.2"]
                                    [hashp "0.2.2"]]

                   :injections     [(require 'hashp.core)]

                   :aliases        {"clean-ns"     ["clojure-lsp" "clean-ns" "--dry"] ;; check if namespaces are clean
                                    "format"       ["clojure-lsp" "format" "--dry"] ;; check if namespaces are formatted
                                    "diagnostics"  ["clojure-lsp" "diagnostics"]
                                    "lint"         ["do" ["clean-ns"] ["format"] ["diagnostics"]]
                                    "clean-ns-fix" ["clojure-lsp" "clean-ns"]
                                    "format-fix"   ["clojure-lsp" "format"]
                                    "lint-fix"     ["do" ["clean-ns-fix"] ["format-fix"]]}}})
