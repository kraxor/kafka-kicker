package ch.sonofabit.kafka_kicker.service

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.junit.jupiter.api.Test

@QuarkusTest
class KickerFactoryTest {
    @Inject
    lateinit var kickerFactory: KickerFactory

    @ConfigProperty(name = "quarkus.kafka.devservices.port")
    lateinit var port: String

    @Test
    fun listTopics() {
        kickerFactory.get("localhost", port.toInt()).run {
            listTopics().also {
                it.size shouldBe 2
                it shouldContain "words-in"
                it shouldContain "words-out"
            }
        }
    }
}
