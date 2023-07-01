package br.com.fiap.gerenciamentopedidos.infrastructure.repositories

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface PedidoJpaRepository : JpaRepository<PedidoEntity, Long> {

    fun findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
        status: PedidoStatus,
        dataInicial: OffsetDateTime,
        dataFinal: OffsetDateTime
    ) : List<PedidoEntity>

}
