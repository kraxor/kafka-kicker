package ch.sonofabit.kafka_kicker.service


import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntity
import io.quarkus.hibernate.reactive.rest.data.panache.PanacheRepositoryResource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Entity
import kotlinx.serialization.Serializable

@Entity
@Serializable
open class Connection : PanacheEntity() {
    open lateinit var name: String
    open lateinit var bootstrapServers: String
}

@ApplicationScoped
class ConnectionRepository : PanacheRepository<Connection>

interface ConnectionResource : PanacheRepositoryResource<ConnectionRepository, Connection, Long>
