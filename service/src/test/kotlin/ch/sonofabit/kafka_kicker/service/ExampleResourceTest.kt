package ch.sonofabit.kafka_kicker.service

import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test

@QuarkusTest
open class ExampleResourceTest {
    @Test
    fun testHelloEndpoint() {
        When {
            get("/hello")
        }.Then {
            statusCode(200)
            body(CoreMatchers.`is`("Hello from RESTEasy Reactive"))
        }
    }
}
