package ch.sonofabit.kafka.kicker.lib

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.AdminClientConfig
import java.util.*

class Kicker(brokerHost: String = "localhost", brokerPort: Int = 9092) : AutoCloseable {
    private val adminClient: AdminClient

    init {
        val config = Properties()
        config[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = "$brokerHost:$brokerPort"
        adminClient = AdminClient.create(config)
    }

    fun listTopics(): Set<String> = adminClient.listTopics().names().get()

    override fun close() = adminClient.close()
}
