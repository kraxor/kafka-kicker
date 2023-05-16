package ch.sonofabit.kafka_kicker.service

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test

@QuarkusTest
open class ExampleResourceTest {
    @Test
    fun testHelloEndpoint() {
        RestAssured.given()
            .`when`()["/hello"]
            .then()
            .statusCode(200)
            .body(CoreMatchers.`is`("Hello from RESTEasy Reactive"))
    }
}