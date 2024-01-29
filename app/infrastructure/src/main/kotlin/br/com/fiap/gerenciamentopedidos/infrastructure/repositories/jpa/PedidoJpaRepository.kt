package br.com.fiap.gerenciamentopedidos.infrastructure.repositories.jpa

import br.com.fiap.gerenciamentopedidos.infrastructure.entities.PedidoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PedidoJpaRepository : JpaRepository<PedidoEntity, Long> {
    fun findByReferencia(referencia: String): Optional<PedidoEntity>

    @Query(
        "SELECT IFNULL(MAX(p.numero),0) FROM pedido p WHERE DATE(p.data_hora) = CURDATE()",
        nativeQuery = true
    )
    fun obterUltimoNumeroPedidoDoDia(): String
}
