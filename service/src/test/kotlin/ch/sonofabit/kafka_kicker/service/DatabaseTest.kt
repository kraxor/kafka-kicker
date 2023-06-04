package ch.sonofabit.kafka_kicker.service

import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.quarkus.test.vertx.UniAsserterInterceptor
import io.smallrye.mutiny.Uni
import org.junit.jupiter.api.Test
import java.util.function.Supplier

@QuarkusTest
class DatabaseTest {
    @Test
    @RunOnVertxContext
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

//        connection.persist<Connection>()

        asserter.transactional {
            assertNotNull { connection.persist() }
        }


//        asserter.transactional {}
    }

    //    @WithSession
    class TransactionalAsserter(asserter: UniAsserter) : UniAsserterInterceptor(asserter) {
        override fun <T> transformUni(uniSupplier: Supplier<Uni<T>>): Supplier<Uni<T>> =
            Supplier {
//                Panache.withSession {
                Panache.withTransaction(uniSupplier)
//                }
            }
    }

    class TransactionalUniAsserterInterceptor(asserter: UniAsserter) : UniAsserterInterceptor(asserter) {

        //        @Transactional
        override fun <T> transformUni(uniSupplier: Supplier<Uni<T>>): Supplier<Uni<T>> {
            // Assert/execute methods are invoked within a database transaction
            return Supplier { Panache.withTransaction(uniSupplier) }
        }
    }

    private fun UniAsserter.transactional(block: TransactionalAsserter.() -> Unit): Unit =
        TransactionalAsserter(this).block()
}
