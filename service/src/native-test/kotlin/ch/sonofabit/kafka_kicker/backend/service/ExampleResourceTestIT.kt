package ch.sonofabit.kafka_kicker.backend.service

import io.quarkus.test.junit.QuarkusIntegrationTest

@QuarkusIntegrationTest
class ExampleResourceIT : ExampleResourceTest() { // Execute the same tests but in packaged mode.
}
