package ch.sonofabit.kafka_kicker.service.api.connection.resource

import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test

@QuarkusTest
class DeleteConnectionResourceTest : ConnectionResourceTest() {

    @Test
    fun `DELETE should return 204 and delete record`() {
        val id = mapOf(
            "name" to "get me!",
            "bootstrapServers" to "broker:1337",
        ).postAndExtract().id!!

        When { get("/connection/${id}") }.Then { statusCode(200) }
        When { delete("/connection/${id}") }.Then { statusCode(204).body(empty()) }
        When { get("/connection/${id}") }.Then { statusCode(404) }
    }

    @Test
    fun `DELETE without parameters should return 405`() {
        When { delete("/connection/") }.Then { statusCode(405).body(empty()) }
    }
}
