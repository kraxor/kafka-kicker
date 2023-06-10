package ch.sonofabit.kafka_kicker.service

import ch.sonofabit.kafka_kicker.service.util.transactional
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.path.json.JsonPath
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@QuarkusTest
class ConnectionResourceTest {
    @BeforeEach
    @RunOnVertxContext
    fun beforeEach(asserter: UniAsserter) {
        asserter.transactional { assertNotNull { Connection.deleteAll() } }
    }

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
        }.Extract { JsonPath(asString()).getList("", Connection::class.java) }.apply {
            size shouldBe 0
        }
    }
}

private fun empty() = `is`("")
private fun ExtractableResponse<Response>.connections() = JsonPath(asString()).getList("", Connection::class.java)
private fun ExtractableResponse<Response>.connection(): Connection = `as`(Connection::class.java)
private fun getAndExtract(id: Long) = When { get("/connection/${id}") }.Extract { `as`(Connection::class.java) }
private fun Map<String, String>.postAndExtract() =
    let { request -> Given { body(request).contentType(JSON) }.When { post("/connection/") }.Extract { connection() } }
