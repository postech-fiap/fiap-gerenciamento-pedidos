package br.com.fiap.gerenciamentopedidos.api.responses

import br.com.fiap.gerenciamentopedidos.domain.models.Item

class PedidoProdutoResponse(pedidoProduto: Item) {
    val nome = pedidoProduto.produto?.nome
    val valorPago = pedidoProduto.valorPago
    val quantidade = pedidoProduto.quantidade
    val comentario = pedidoProduto.comentario
}
