[![Java CI with Gradle](https://github.com/kraxor/kafka-kicker/actions/workflows/gradle.yml/badge.svg)](https://github.com/kraxor/kafka-kicker/actions/workflows/gradle.yml)

# kafka-kicker

A tool for breaking and fixing your Kafka setup in reproducible ways.

Tech stack TLDR:
* Gradle 8.1.1
* Kotlin 1.8.10
* Apache Kafka 3.2.3
* Quarkus 3.0.3.Final
    * RESTEasy Reactive
    * Hibernate Reactive REST Data with Panache
    * Smallrye OpenAPI (OpenAPI V3)
* Postgres (latest alpine)
* Docker (Compose V2)

# Quick Start

All profiles use the default debugger port 5005.

### Development

In IntelliJ, run `START [dev mode]` to execute the code generation tasks and start the backend and the frontend in dev mode.

* frontend
  * UI: http://localhost:3000
* backend
  * welcome page: http://localhost:8080
  * Dev UI: http://localhost:8080/q/dev
  * Sagger UI: http://localhost:8080/q/swagger-ui
  * continuous testing: http://localhost:8080/q/dev-ui/continuous-testing

Alternatively, you can execute these steps individually:
* in `/`:
  * `./gradlew :sdk:generate_typescript-axios`
  * `./gradlew :service:quarkusDev`
* in `/ui`
  * `npm install`
  * `npm start`

### Continuous testing

Standard Quarkus Test mode with continuous testing. Requires docker daemon.

Start using IntelliJ `TEST [quarkusTest]` or from the terminal `$ ./gradlew quarkusTest`

### Production

Essentially `quarkusDev` but uses the services defined in `docker-compose.yml` instead of Quarkus Dev Services.
This is temporary, until a proper production build is configured.

Start using IntelliJ `PROD [quarkusDev]` or the
terminal `$ docker compose up -d && QUARKUS_PROFILE=prod ./gradlew quarkusDev`

# License

TBA
