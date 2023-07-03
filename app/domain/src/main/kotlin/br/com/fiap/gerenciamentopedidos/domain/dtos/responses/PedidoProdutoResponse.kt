package br.com.fiap.gerenciamentopedidos.domain.dtos.responses

import br.com.fiap.gerenciamentopedidos.domain.models.PedidoProduto
import java.math.BigDecimal

class PedidoProdutoResponse(pedidoProduto: PedidoProduto) {
    val nome: String?
    val valorPago: BigDecimal?
    val quantidade: Int?
    val comentario: String?

    init {
        nome = pedidoProduto.produto!!.nome
        valorPago = pedidoProduto.valorPago
        quantidade = pedidoProduto.quantidade
        comentario = pedidoProduto.comentario
    }
}
