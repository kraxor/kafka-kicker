package ch.sonofabit.kafka_kicker.service.api

import ch.sonofabit.kafka_kicker.service.api.util.transactional
import io.kotest.matchers.shouldNotBe
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import org.junit.jupiter.api.Test

@QuarkusTest
class DatabaseTest {
    @Test
    @RunOnVertxContext
    fun `entities can be persisted`(asserter: UniAsserter): Unit = asserter.run {
        val connection = Connection().apply {
            name = "test"
            bootstrapServers = "broker:1337"
        }

        transactional { assertNotNull { connection.persist() } }
        execute { connection.id shouldNotBe null }
    }
}
