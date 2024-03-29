package br.com.fiap.gerenciamentopedidos.api.requests

import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Produto
import java.math.BigDecimal

data class CadastrarPedidoProdutoRequest(
    val produtoId: Long,
    val quantidade: Long,
    val comentario: String? = null
) {
    init {
        require(produtoId > 0) { "O id do produto deve ser informado" }
        require(quantidade > 0) { "A quantidade deve ser maior que zero" }
        if (comentario.isNullOrEmpty().not()) {
            require(comentario!!.length <= 100) { "Comentário muito extenso" }
        }
    }

    fun toModel() = Item(
        id = produtoId,
        quantidade = quantidade,
        comentario = comentario,
        valorPago = BigDecimal.ZERO,
        produto = Produto(id = produtoId)
    )
}
