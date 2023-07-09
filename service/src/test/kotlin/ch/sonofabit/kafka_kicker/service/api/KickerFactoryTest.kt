package ch.sonofabit.kafka_kicker.service.api

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.admin.NewTopic
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@QuarkusTest
class KickerFactoryTest {
    @Inject
    lateinit var kickerFactory: KickerFactory

    @ConfigProperty(name = "quarkus.kafka.devservices.port")
    lateinit var port: String

    @BeforeEach
    fun setUp() {
        AdminClient.create(mapOf(BOOTSTRAP_SERVERS_CONFIG to "localhost:${port}")).run {
            deleteTopics(listTopics().names().get())
            createTopics(listOf(NewTopic("quarkus-test-topic", 1, 1.toShort()))).all().get()
            close()
        }
    }

    @Test
    fun listTopics() {
        kickerFactory.get("localhost", port.toInt()).run {
            listTopics().also {
                it.size shouldBe 1
                it shouldContain "quarkus-test-topic"
            }
        }
    }
}
