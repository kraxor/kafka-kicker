package ch.sonofabit.kafka_kicker.service

import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.quarkus.test.vertx.UniAsserterInterceptor
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.smallrye.mutiny.Uni
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import java.util.function.Supplier

@QuarkusTest
class ConnectionResourceTest {
    @Test
    @RunOnVertxContext
    fun `returns 200 when entity is found`(asserter: UniAsserter) {
        asserter.transactional {
            val connection = Connection().apply {
                name = "test"
                bootstrapServers = "broker:1337"
            }

            assertNotNull { connection.persistAndFlush() }

            execute {
                When {
                    get("/connection/${connection.id!!}")
                }.Then {
                    statusCode(200)
                    body(`is`("{\"name\":\"test\",\"bootstrapServers\":\"broker:1337\"}"))
                }
            }
        }
    }

    //    @Test
//    @RunOnVertxContext
    fun `entities can be persisted`(asserter: UniAsserter) {
//        TransactionalUniAsserterInterceptor(asserter).run {
//            val connection = Connection().apply {
//                name = "test"
//                bootstrapServers = "broker:1337"
//            }
//        }


        val connection = Connection().apply {
            name = "test"
            bootstrapServers = "broker:1337"
        }

        connection.persist<Connection>()

//        asserter.transactional {
//            assertNotNull { connection.persist() }
//        }


//        asserter.transactional {}
    }

    class TransactionalAsserter(asserter: UniAsserter) : UniAsserterInterceptor(asserter) {
        override fun <T> transformUni(uniSupplier: Supplier<Uni<T>>): Supplier<Uni<T>> =
            Supplier { Panache.withSession { Panache.withTransaction(uniSupplier) } }
    }

    class TransactionalUniAsserterInterceptor(asserter: UniAsserter) : UniAsserterInterceptor(asserter) {

        //        @Transactional
        override fun <T> transformUni(uniSupplier: Supplier<Uni<T>>): Supplier<Uni<T>> {
            // Assert/execute methods are invoked within a database transaction
            return Supplier { Panache.withTransaction(uniSupplier) }
        }
    }

    private fun UniAsserter.transactional(block: TransactionalAsserter.() -> Unit) =
        TransactionalAsserter(this).block().let { this }
}