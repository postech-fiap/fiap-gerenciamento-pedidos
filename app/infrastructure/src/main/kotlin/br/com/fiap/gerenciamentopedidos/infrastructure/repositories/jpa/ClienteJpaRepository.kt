package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa

import br.com.fiap.gerenciamentopedidos.infrastructure.entities.ClienteEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClienteJpaRepository : JpaRepository<ClienteEntity, Long> {

    fun findByCpf(cpf: String): Optional<ClienteEntity>
}
