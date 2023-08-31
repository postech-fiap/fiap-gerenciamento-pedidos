package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PagamentoEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface PagamentoJpaRepository : JpaRepository<PagamentoEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE PagamentoEntity p SET p.status = :status WHERE p.pedido.id = :id")
    fun updateStatusByPedidoId(status: PagamentoStatus, id: Long)
}
