package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import java.math.BigDecimal

data class PedidoProduto(
    val id: Long? = null,
    val pedido: Pedido?,
    val produto: Produto?,
    val valorPago: BigDecimal,
    val quantidade: Int,
    val comentario: String?,
) {

}
