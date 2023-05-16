package ch.sonofabit.kafka_kicker.backend.service

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/hello")
class ExampleResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): String = "Hello from RESTEasy Reactive"
}
