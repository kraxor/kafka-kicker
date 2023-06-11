[![Java CI with Gradle](https://github.com/kraxor/kafka-kicker/actions/workflows/gradle.yml/badge.svg)](https://github.com/kraxor/kafka-kicker/actions/workflows/gradle.yml)

# kafka-kicker

A tool for breaking and fixing your Kafka setup in reproducible ways.

Tech stack TLDR:
* Kotlin
* Apache Kafka
* Quarkus 3
  * RESTEasy Reactive
  * Hibernate Reactive REST Data with Panache
* Postgres
* Docker

# Quick Start

| mode                                      | IntelliJ             | Gradle        | `QUARKUS_PROFILE` | External Services    |
|-------------------------------------------|----------------------|---------------|-------------------|----------------------|
| [development](#development)               | `DEV [quarkusDev]`   | `quarkusDev`  | `dev`             | provided (Quarkus)   |
| [continuous testing](#continuous-testing) | `TEST [quarkusTest]` | `quarkusTest` | `test`            | provided (Quarkus)   |
| [production](#production)                 | `PROD [quarkusDev]`  | `quarkusDev`  | `prod`            | `docker-compose.yml` |   

All profiles use the default debugger port 5005.

### Development

Standard Quarkus Dev mode with live reloading, Dev UI and Dev Services enabled. Requires docker daemon.

Start using IntelliJ `DEV [quarkusDev]` or from the terminal `$ ./gradlew quarkusDev`

Dev UI: http://localhost:8080/q/dev

Swagger UI: http://localhost:8080/q/swagger-ui


### Continuous testing

Standard Quarkus Test mode with continuous testing. Requires docker daemon.

Start using IntelliJ `TEST [quarkusTest]` or from the terminal `$ ./gradlew quarkusTest`

### Production

Essentially `quarkusDev` but uses the services defined in `docker-compose.yml` instead of Quarkus Dev Services.
This is temporary, until a proper production build is configured.

Start using IntelliJ `PROD [quarkusTest]` or the
terminal `$ docker compose up -d && QUARKUS_PROFILE=prod ./gradlew quarkusDev`

# License

TBA
