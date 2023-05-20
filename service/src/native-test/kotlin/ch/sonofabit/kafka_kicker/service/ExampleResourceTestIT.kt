package ch.sonofabit.kafka_kicker.service

import io.quarkus.test.junit.QuarkusIntegrationTest

@QuarkusIntegrationTest
class ExampleResourceIT : ExampleResourceTest() { // Execute the same tests but in packaged mode.
}
