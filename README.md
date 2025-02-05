[![Clojars Project](https://img.shields.io/clojars/v/net.clojars.macielti/prometheus-component.svg)](https://clojars.org/net.clojars.macielti/prometheus-component)
![Compatible with GraalVM](https://img.shields.io/badge/compatible_with-GraalVM-green)

# Prometheus Component

This is a component that allows you to monitor your application using Prometheus.

## Default Metrics

The following default metrics are defined:

- **http-request-response**
    - **Type**: Counter
    - **Labels**: `:status`, `:service`, `:endpoint`
    - **Description**: Counts the number of HTTP requests and their
      responses ([HTTP Client Component](https://github.com/macielti/http-client-component)).

- **http-request-response-timing**
    - **Type**: Summary
    - **Labels**: `:service`, `:endpoint`
    - **Quantiles**:
        - 0.5: 0.05
        - 0.9: 0.1
        - 0.99: 0.001
    - **Description**: Measures the response time of HTTP
      requests ([HTTP Client Component](https://github.com/macielti/http-client-component)).

- **http-request-in-handle-timing-v2**
    - **Type**: Summary
    - **Labels**: `:service`, `:endpoint`
    - **Quantiles**:
        - 0.5: 0.05
        - 0.9: 0.1
        - 0.99: 0.001
    - **Description**: Measures the time taken to handle HTTP
      requests ([Service Component](https://github.com/macielti/service-component)).

- **job-execution-timing**
    - **Type**: Summary
    - **Labels**: `:service`, `:job-id`
    - **Quantiles**:
        - 0.5: 0.05
        - 0.9: 0.1
        - 0.99: 0.001
    - **Description**: Measures the time taken to execute a
      job ([Scheduler Component](https://github.com/macielti/scheduler-component)).

- **elapsed-time**
    - **Type**: Summary
    - **Labels**: `:id`
    - **Quantiles**:
        - 0.5: 0.05
        - 0.9: 0.1
        - 0.99: 0.001
    - **Description**: Measures the elapsed time to run a given body of code.

## License

Copyright © 2024 Bruno do Nascimento

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
