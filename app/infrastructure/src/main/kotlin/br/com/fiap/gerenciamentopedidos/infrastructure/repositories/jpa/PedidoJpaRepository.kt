package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.OffsetDateTime

interface PedidoJpaRepository : JpaRepository<PedidoEntity, Long> {

    fun findByStatusAndDataHoraGreaterThanEqualAndDataHoraLessThanEqual(
        status: PedidoStatus,
        dataInicial: OffsetDateTime,
        dataFinal: OffsetDateTime
    ): List<PedidoEntity>

    @Modifying
    @Query("UPDATE PedidoEntity p SET p.status = :status WHERE p.id = :id")
    fun updateStatusById(status: PedidoStatus, id: Long)

    @Query("SELECT IFNULL(MAX(CAST(p.numero AS UNSIGNED)),0) FROM pedido p WHERE DATE(p.data_hora) = CURDATE()", nativeQuery = true)
    fun obterUtimoNumeroPedidoDoDia(): String
}
