package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.models.PedidoProduto
import java.math.BigDecimal

data class PedidoProdutoDto(
    val id: Long? = null,
    val produto: ProdutoDto?,
    val valorPago: BigDecimal,
    val quantidade: Int,
    val comentario: String?,
) {
    companion object {
        fun fromModel(pedidoProduto: PedidoProduto) = PedidoProdutoDto(
            pedidoProduto.id,
            pedidoProduto.produto?.let { ProdutoDto.fromModel(it) },
            pedidoProduto.valorPago,
            pedidoProduto.quantidade,
            pedidoProduto.comentario
        )
    }

    fun toModel() = PedidoProduto(id, produto?.toModel(), valorPago, quantidade, comentario)
}
