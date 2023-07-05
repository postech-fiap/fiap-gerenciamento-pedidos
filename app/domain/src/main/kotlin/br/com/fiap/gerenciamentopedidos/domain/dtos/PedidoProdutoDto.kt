package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.models.PedidoProduto
import java.math.BigDecimal

data class PedidoProdutoDto(
    val id: Long? = null,
    val produto: ProdutoDto,
    val quantidade: Long,
    val comentario: String? = null,
    val valorPago: BigDecimal? = null
) {
    companion object {
        fun fromModel(pedidoProduto: PedidoProduto) = PedidoProdutoDto(
            pedidoProduto.id,
            pedidoProduto.produtoId?.let { ProdutoDto(it) }!!,
            pedidoProduto.quantidade,
            pedidoProduto.comentario,
            pedidoProduto.produto?.valor
        )
    }

    fun toModel() = PedidoProduto(id, produto.id, quantidade, comentario)
}
