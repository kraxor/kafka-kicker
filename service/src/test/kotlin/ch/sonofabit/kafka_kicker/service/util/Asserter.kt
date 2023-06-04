package ch.sonofabit.kafka_kicker.service.util

import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.test.vertx.UniAsserter
import io.quarkus.test.vertx.UniAsserterInterceptor
import io.smallrye.mutiny.Uni
import java.util.function.Supplier

class Asserter {
    class TransactionalAsserter(asserter: UniAsserter) : UniAsserterInterceptor(asserter) {
        override fun <T> transformUni(uniSupplier: Supplier<Uni<T>>): Supplier<Uni<T>> =
            Supplier { Panache.withSession { Panache.withTransaction(uniSupplier) } }
    }
}

fun UniAsserter.transactional(block: Asserter.TransactionalAsserter.() -> Unit) =
    Asserter.TransactionalAsserter(this).block().let { this }

