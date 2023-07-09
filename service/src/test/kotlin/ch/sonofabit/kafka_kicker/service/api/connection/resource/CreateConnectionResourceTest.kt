package ch.sonofabit.kafka_kicker.service.api.connection.resource

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test

@QuarkusTest
class CreateConnectionResourceTest : ConnectionResourceTest() {

    @Test
    fun `POST should return 201 and persist record`() {
        val request = mapOf(
            "name" to "persist me!",
            "bootstrapServers" to "broker:1338",
        )

        val response = Given {
            contentType(JSON)
            body(request)
        }.When { post("/connection") }.Then {
            statusCode(201)
            contentType(JSON)
        }.Extract { connection() }.apply {
            id shouldNotBe null
            name shouldBe request["name"]
            bootstrapServers shouldBe request["bootstrapServers"]
        }

        getAndExtract(response.id!!).apply {
            name shouldBe request["name"]
            bootstrapServers shouldBe request["bootstrapServers"]
        }
    }
}
