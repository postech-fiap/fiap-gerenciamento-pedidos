package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa

import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PedidoJpaRepository : JpaRepository<PedidoEntity, Long> {
    @Query(
        "SELECT IFNULL(MAX(CAST(p.numero AS UNSIGNED)),0) FROM pedido p WHERE DATE(p.data_hora) = CURDATE()",
        nativeQuery = true
    )
    fun obterUltimoNumeroPedidoDoDia(): String
}
