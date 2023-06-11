package ch.sonofabit.kafka_kicker.service


import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntity
import io.quarkus.hibernate.reactive.rest.data.panache.PanacheRepositoryResource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Entity

@Entity
open class Connection : PanacheEntity() {
    companion object: PanacheCompanion<Connection>
    open lateinit var name: String
    open lateinit var bootstrapServers: String
}

@ApplicationScoped
class ConnectionRepository : PanacheRepository<Connection>

@Suppress("unused")
interface ConnectionResource : PanacheRepositoryResource<ConnectionRepository, Connection, Long>
