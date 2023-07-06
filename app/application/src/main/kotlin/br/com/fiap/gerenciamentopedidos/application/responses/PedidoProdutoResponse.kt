package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoProdutoDto
import br.com.fiap.gerenciamentopedidos.domain.models.PedidoProduto
import java.math.BigDecimal

data class PedidoProdutoResponse(private val pedidoProduto: PedidoProdutoDto) {

    val nome: String
    val valorPago: BigDecimal
    val quantidade: Long
    val comentario: String?

    init {
        nome = pedidoProduto.produto.nome!!
        valorPago = pedidoProduto.valorPago!!
        quantidade = pedidoProduto.quantidade
        comentario = pedidoProduto.comentario
    }

}
