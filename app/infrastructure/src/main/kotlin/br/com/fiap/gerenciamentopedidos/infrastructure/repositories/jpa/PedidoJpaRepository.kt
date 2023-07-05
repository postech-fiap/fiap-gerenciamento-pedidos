package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.util.Optional

interface PedidoJpaRepository : JpaRepository<PedidoEntity, Long> {

    fun findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
        status: PedidoStatus,
        dataInicial: OffsetDateTime,
        dataFinal: OffsetDateTime
    ): List<PedidoEntity>


    @Query("SELECT p FROM PedidoEntity p WHERE p.dataHora >= :dataInicio AND p.dataHora <= :dataFim")
    fun findByDataHora(dataInicio: OffsetDateTime, dataFim: OffsetDateTime): List<PedidoEntity>

   // fun findByDataHora(dataHoraInicio: OffsetDateTime, dataHoraFim: OffsetDateTime): List<PedidoEntity>

}
