package ch.sonofabit.kafka_kicker.backend.service

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.eclipse.microprofile.reactive.messaging.Message
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@QuarkusTest
internal class MyReactiveMessagingApplicationTest {
    @Inject
    lateinit var application: MyReactiveMessagingApplication

    @Test
    fun test() {
        Assertions.assertEquals("HELLO", application.toUpperCase(Message.of("Hello")).payload)
        Assertions.assertEquals("BONJOUR", application.toUpperCase(Message.of("bonjour")).payload)
    }
}
