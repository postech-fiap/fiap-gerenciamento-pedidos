package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface PedidoJpaRepository : JpaRepository<PedidoEntity, Long> {

    @Query("SELECT pedido " +
            "FROM PedidoEntity pedido " +
            "JOIN FETCH pedido.cliente cliente " +
            "JOIN FETCH pedido.produtos pedido_produto " +
            "JOIN FETCH pedido_produto.produto produto " +
            "JOIN FETCH produto.imagem produto_imagem " +
            "WHERE pedido.status <> 'FINALIZADO' " +
            "ORDER BY FIELD (pedido.status, 'PRONTO', 'EM_PREPARACAO', 'RECEBIDO')")
    fun buscarPedidos(): List<PedidoEntity>

    @Transactional
    @Modifying
    @Query("UPDATE PedidoEntity p SET p.status = :status WHERE p.id = :id")
    fun updateStatusById(status: PedidoStatus, id: Long)

    @Query("SELECT IFNULL(MAX(CAST(p.numero AS UNSIGNED)),0) FROM pedido p WHERE DATE(p.data_hora) = CURDATE()", nativeQuery = true)
    fun obterUltimoNumeroPedidoDoDia(): String
}
