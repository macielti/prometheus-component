# Change Log

All notable changes to this project will be documented in this file. This change log follows the conventions
of [keepachangelog.com](http://keepachangelog.com/).

## 1.4.1-1 - 2025-03-04 (beta)

### Added

- Now you can define metrics from the configuration
  file ([Config Component](https://github.com/macielti/common-clj/blob/main/src/common_clj/integrant_components/config.clj)).

## 1.3.1-1 - 2025-02-16 (beta)

### Changed

- Refactor the `with-elapsed-time` macro to be `report-elapsed-time!` use the proper `System/nanoTime`.

## 0.3.1-1 - 2025-02-05 (beta)

### Added

- Added `:percentage-used-memory-host` metric to measure the percentage of used memory of the host.

## 0.3.1 - 2025-02-05

### Added

- Added macro `with-elapsed-time` to measure the elapsed time of a given body of code and report it to the Prometheus.
- Added default metric about the elapsed time of a given body of code (`:elapsed-time`).

## 0.2.1 - 2025-02-01

### Added

- Added default metric about the response time of the out HTTP Requests (`:http-request-response-timing`).

## 0.1.1 - 2025-01-30

### Added

- Added `:job-execution-timing` metric to measure the time it takes to execute a job.

### Changed

- Bump Dependencies.

## 0.1.0 - 2024-11-19

### Added

- Initial release.
