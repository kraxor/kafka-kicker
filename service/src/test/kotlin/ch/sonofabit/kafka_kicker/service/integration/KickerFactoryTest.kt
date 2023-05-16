package ch.sonofabit.kafka_kicker.service.integration

import io.kotest.matchers.shouldBe
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@QuarkusTest
class KickerFactoryTest {
    @Inject
    lateinit var kickerFactory: KickerFactory

    @Test
    fun listTopics() {
        kickerFactory.get("localhost", 9092).listTopics().run {
            size shouldBe 1
            first() shouldBe "test-topic"
        }
    }
}
