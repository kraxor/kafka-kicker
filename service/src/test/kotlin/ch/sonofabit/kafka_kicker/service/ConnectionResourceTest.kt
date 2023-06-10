package ch.sonofabit.kafka_kicker.service

import ch.sonofabit.kafka_kicker.service.util.transactional
import io.kotest.matchers.shouldBe
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.path.json.JsonPath
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class ConnectionResourceTest {
    @Test
    @RunOnVertxContext
    fun `GET returns 200 when record is found`(asserter: UniAsserter): Unit = asserter.run {
        val expected = Connection().apply {
            name = "get me!"
            bootstrapServers = "broker:1337"
        }
        transactional { assertNotNull { expected.persistAndFlush() } }

        execute {
            When { get("/connection/${expected.id}") }.Then {
                statusCode(200)
                contentType(JSON)
            }.Extract { `as`(Connection::class.java) }.apply {
                id shouldBe expected.id
                name shouldBe expected.name
                bootstrapServers shouldBe expected.bootstrapServers
            }
        }
    }

    @Test
    @RunOnVertxContext
    fun `GET returns 404 when record is not found`(asserter: UniAsserter): Unit = asserter.run {
        execute {
            When { get("/connection/666") }.Then {
                statusCode(404)
                body(`is`(""))
            }
        }
    }

    @Test
    @RunOnVertxContext
    fun `POST returns 201 and record is persisted`(asserter: UniAsserter): Unit = asserter.run {
        val request = mapOf(
            "name" to "persist me!",
            "bootstrapServers" to "broker:1338",
        )
        var id: Long? = null

        execute {
            id = Given {
                contentType(JSON)
                body(request)
            }.When { post("/connection") }.Then {
                statusCode(201)
                contentType(JSON)
            }.Extract { JsonPath(asString()).getLong("id") }
        }

        execute {
            When { get("/connection/${id!!}") }.Extract { `as`(Connection::class.java) }.apply {
                name shouldBe request["name"]
                bootstrapServers shouldBe request["bootstrapServers"]
            }
        }
    }

    @Test
    @RunOnVertxContext
    fun `PUT returns 204 and record is updated`(asserter: UniAsserter): Unit = asserter.run {
        val connection = Connection().apply {
            name = "update me!"
            bootstrapServers = "broker:1234"
        }.apply { transactional { assertNotNull { persistAndFlush() } } }

        val request = mapOf(
            "id" to connection.id,
            "name" to "something else",
            "bootstrapServers" to "broker:1336",
        )

        execute {
            Given {
                contentType(JSON)
                body(request)
            }.When { put("/connection/${connection.id!!}") }.Then {
                statusCode(204)
                body(`is`(""))
            }
        }

        execute {
            When { get("/connection/${connection.id!!}") }.Extract { `as`(Connection::class.java) }.apply {
                name shouldBe request["name"]
                bootstrapServers shouldBe request["bootstrapServers"]
            }
        }
    }

    @Test
    @RunOnVertxContext
    fun `DELETE returns 204 and record is deleted`(asserter: UniAsserter): Unit = asserter.run {
        val connection = Connection().apply {
            name = "get me!"
            bootstrapServers = "broker:1337"
        }
        transactional { assertNotNull { connection.persistAndFlush() } }

        execute { When { get("/connection/${connection.id}") }.Then { statusCode(200) } }
        execute { When { delete("/connection/${connection.id}") }.Then { statusCode(204) } }
        execute { When { get("/connection/${connection.id}") }.Then { statusCode(404) } }
    }
}
