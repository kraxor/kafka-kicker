package ch.sonofabit.kafka_kicker.service.api.connection.resource

import ch.sonofabit.kafka_kicker.service.api.Connection
import ch.sonofabit.kafka_kicker.service.api.util.transactional
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import io.restassured.path.json.JsonPath
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher
import org.junit.jupiter.api.BeforeEach

@QuarkusTest
abstract class ConnectionResourceTest {

    @BeforeEach
    @RunOnVertxContext
    fun beforeEach(asserter: UniAsserter) {
        asserter.transactional { assertNotNull { Connection.deleteAll() } }
    }

    protected fun empty(): Matcher<String> = `is`("")

    protected fun getAndExtract(id: Long): Connection =
        When { get("/connection/${id}") }.Extract { connection() }

    protected fun Map<String, String>.postAndExtract() = let { request ->
        Given { body(request).contentType(JSON) }.When { post("/connection/") }.Extract { connection() }
    }

    protected fun ExtractableResponse<Response>.connections(): List<Connection> =
        JsonPath(asString()).getList("", Connection::class.java)

    protected fun ExtractableResponse<Response>.connection(): Connection = `as`(Connection::class.java)
}
