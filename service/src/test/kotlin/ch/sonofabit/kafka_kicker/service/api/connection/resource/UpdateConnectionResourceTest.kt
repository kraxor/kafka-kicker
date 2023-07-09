package ch.sonofabit.kafka_kicker.service.api.connection.resource

import io.kotest.matchers.shouldBe
import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test

@QuarkusTest
class UpdateConnectionResourceTest : ConnectionResourceTest() {
    @Test
    fun `PUT should return 204 and update record`() {
        val id = mapOf(
            "name" to "update me!",
            "bootstrapServers" to "broker:1234",
        ).postAndExtract().id!!

        val request = mapOf(
            "id" to id,
            "name" to "something else",
            "bootstrapServers" to "broker:1336",
        )

        Given {
            contentType(JSON)
            body(request)
        }.When { put("/connection/${id}") }.Then {
            statusCode(204)
            body(empty())
        }

        getAndExtract(id).apply {
            name shouldBe request["name"]
            bootstrapServers shouldBe request["bootstrapServers"]
        }
    }

    @Test
    fun `PUT without parameters should return 405`() {
        val request = mapOf(
            "id" to "1337",
            "name" to "something else",
            "bootstrapServers" to "broker:1336",
        )

        Given {
            contentType(JSON)
            body(request)
        }.When { put("/connection/") }.Then {
            statusCode(405)
            body(empty())
        }
        When { delete("/connection/") }.Then { statusCode(405).body(empty()) }
    }
}
