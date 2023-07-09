package ch.sonofabit.kafka_kicker.service.core

import ch.sonofabit.kafka_kicker.service.core.Kicker
import io.kotest.matchers.shouldBe
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.admin.NewTopic
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
class KickerTest {
    private lateinit var underTest: Kicker

    companion object {
        @Container
        val kafkaContainer: KafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.2"))
            .withReuse(true)
    }


    @BeforeEach
    fun setUp() {

        AdminClient.create(mapOf(BOOTSTRAP_SERVERS_CONFIG to "${kafkaContainer.host}:${kafkaContainer.firstMappedPort}"))
            .run {
                createTopics(listOf(NewTopic("test-topic", 1, 1.toShort()))).all().get()
                close()
            }

        underTest = Kicker(kafkaContainer.host, kafkaContainer.firstMappedPort)
    }

    @Test
    fun listTopics() {
        underTest.listTopics().run {
            size shouldBe 1
            first() shouldBe "test-topic"
        }
    }
}
