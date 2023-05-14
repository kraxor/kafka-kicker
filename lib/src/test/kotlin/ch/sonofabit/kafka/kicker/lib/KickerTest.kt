package ch.sonofabit.kafka.kicker.lib

import ch.sonofabit.kafka.kicker.lib.util.ResourceUtils.getResourceFile
import io.kotest.matchers.shouldBe
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.admin.NewTopic
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class KickerTest {
    private lateinit var underTest: Kicker

    companion object {
        const val brokerPort = 9092

        @Container
        val broker: DockerComposeContainer<*> =
            DockerComposeContainer(getResourceFile("docker-compose.yaml"))
                .withExposedService("broker", brokerPort)
    }

    @BeforeEach
    fun setUp() {
        val brokerHost = broker.getServiceHost("broker", brokerPort)

        AdminClient.create(mapOf(BOOTSTRAP_SERVERS_CONFIG to "$brokerHost:${brokerPort}")).run {
            createTopics(listOf(NewTopic("test-topic", 1, 1.toShort()))).all().get()
            close()
        }

        underTest = Kicker(brokerHost, brokerPort)
    }

    @Test
    fun listTopics() {
        underTest.listTopics().run {
            size shouldBe 1
            first() shouldBe "test-topic"
        }
    }
}