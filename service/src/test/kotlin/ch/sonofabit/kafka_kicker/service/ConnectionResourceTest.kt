package ch.sonofabit.kafka_kicker.service

import ch.sonofabit.kafka_kicker.service.util.transactional
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class ConnectionResourceTest {
    @Test
    @RunOnVertxContext
    fun `returns 200 when entity is found`(asserter: UniAsserter) {
        val connection = Connection().apply {
            name = "test"
            bootstrapServers = "broker:1337"
        }

        asserter.transactional {
            assertNotNull { connection.persistAndFlush() }
        }.execute {
            When {
                get("/connection/${connection.id}")
            }.Then {
                statusCode(200)
                body(`is`("{\"name\":\"test\",\"bootstrapServers\":\"broker:1337\"}"))
            }
        }
    }
}
