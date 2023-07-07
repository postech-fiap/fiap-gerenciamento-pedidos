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
            produto = ProdutoDto.fromModel(pedidoProduto.produto!!),
            pedidoProduto.quantidade,
            pedidoProduto.comentario,
            pedidoProduto.valorPago
        )
    }

    fun toModel() = PedidoProduto(
        id,
        quantidade,
        comentario,
        produto.toModel(),
        valorPago
    )
}
