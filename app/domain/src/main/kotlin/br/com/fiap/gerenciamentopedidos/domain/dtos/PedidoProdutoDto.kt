package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.models.PedidoProduto
import java.math.BigDecimal

data class PedidoProdutoDto(
    val id: Long? = null,
    val produto: ProdutoDto,
    val quantidade: Int,
    val comentario: String? = null,
    val valorPago: Double? = null
) {
    companion object {
        fun fromModel(pedidoProduto: PedidoProduto) = PedidoProdutoDto(
            pedidoProduto.id,
            pedidoProduto.produto.let { ProdutoDto.fromModel(it) },
            pedidoProduto.quantidade,
            pedidoProduto.comentario,
            pedidoProduto.getValorTotal()
        )
    }

    fun toModel() = PedidoProduto(id, produto.toModel(), quantidade, comentario)
}
