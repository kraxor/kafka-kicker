package ch.sonofabit.kafka_kicker.service

import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.inject.Inject
import org.eclipse.microprofile.reactive.messaging.*
import java.util.*
import java.util.stream.Stream

@ApplicationScoped
class MyReactiveMessagingApplication {
    @Inject
    @Channel("words-out")
    private lateinit var emitter: Emitter<String>

    /**
     * Sends message to the "words-out" channel, can be used from a JAX-RS resource or any bean of your application.
     * Messages are sent to the broker.
     */
    fun onStart(@Observes ev: StartupEvent?) {
        Stream.of("Hello", "with", "SmallRye", "reactive", "message").forEach { string: String ->
            emitter.send(
                string
            )
        }
    }

    /**
     * Consume the message from the "words-in" channel, uppercase it and send it to the uppercase channel.
     * Messages come from the broker.
     */
    @Incoming("words-in")
    @Outgoing("uppercase")
    fun toUpperCase(message: Message<String>): Message<String> {
        return message.withPayload(message.payload.uppercase(Locale.getDefault()))
    }

    /**
     * Consume the uppercase channel (in-memory) and print the messages.
     */
    @Incoming("uppercase")
    fun sink(word: String) {
        println(">> $word")
    }
}
