package ch.sonofabit.kafka_kicker.service.connection.resource

import ch.sonofabit.kafka_kicker.service.Connection
import io.kotest.matchers.shouldBe
import io.quarkus.test.junit.QuarkusTest
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test

@QuarkusTest
class ListConnectionResourceTest : ConnectionResourceTest() {

    @Test
    fun `GET without parameters should return all records`() {
        val expected = listOf(
            mapOf(
                "name" to "first connection",
                "bootstrapServers" to "broker:1",
            ), mapOf(
                "name" to "second connection",
                "bootstrapServers" to "broker:2",
            )
        )
        val expectedId = expected.map { it.postAndExtract().id }

        When {
            get("/connection/")
        }.Then {
            statusCode(200)
            contentType(JSON)
        }.Extract { connections() }.apply {
            size shouldBe 2
            forEachIndexed { index, response ->
                response.apply {
                    id shouldBe expectedId[index]
                    name shouldBe expected[index]["name"]
                    bootstrapServers shouldBe expected[index]["bootstrapServers"]
                }
            }
        }
    }

    @Test
    fun `GET without parameters should return empty list when there are no records`() {
        When {
            get("/connection/")
        }.Then {
            statusCode(200)
            contentType(JSON)
        }.Extract { jsonPath().getList("", Connection::class.java) }.apply {
            size shouldBe 0
        }
    }
}
