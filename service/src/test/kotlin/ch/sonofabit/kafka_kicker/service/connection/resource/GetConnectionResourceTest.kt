package ch.sonofabit.kafka_kicker.service.connection.resource

import io.kotest.matchers.shouldBe
import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test

@QuarkusTest
class GetConnectionResourceTest : ConnectionResourceTest() {

    @Test
    fun `GET should return 200 when record is found`() {
        val expected = mapOf(
            "name" to "get me!",
            "bootstrapServers" to "broker:1337",
        )
        val id = expected.postAndExtract().id!!

        When { get("/connection/${id}") }.Then {
            statusCode(200)
            contentType(JSON)
        }.Extract { connection() }.apply {
            this.id shouldBe id
            name shouldBe expected["name"]
            bootstrapServers shouldBe expected["bootstrapServers"]
        }
    }

    @Test
    fun `GET should return 404 when record is not found`() {
        When { get("/connection/666") }.Then {
            statusCode(404)
            body(empty())
        }
    }
}
