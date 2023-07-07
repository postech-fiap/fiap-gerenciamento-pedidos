package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.OffsetDateTime

interface PedidoJpaRepository : JpaRepository<PedidoEntity, Long> {

    fun findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
        status: PedidoStatus,
        dataInicial: OffsetDateTime,
        dataFinal: OffsetDateTime
    ): List<PedidoEntity>

    @Query("SELECT CAST(IFNULL(MAX(p.numero),'0') AS UNSIGNED) FROM pedido p WHERE DATE(p.data_hora) = CURDATE()", nativeQuery = true)
    fun obterUtimoNumeroPedidoDoDia(): String
}
