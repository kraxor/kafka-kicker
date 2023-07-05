package ch.sonofabit.kafka_kicker.service.api

import ch.sonofabit.kafka.kicker.lib.Kicker
import jakarta.enterprise.context.ApplicationScoped


@ApplicationScoped
class KickerFactory {
    private val instances = mutableMapOf<Pair<String, Int>, Kicker>()

    fun get(host: String, port: Int): Kicker = instances[host to port] ?: Kicker(host, port)
}
