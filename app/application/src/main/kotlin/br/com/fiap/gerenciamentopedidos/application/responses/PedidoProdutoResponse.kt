package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoProdutoDto

class PedidoProdutoResponse(pedidoProduto: PedidoProdutoDto) {
    val nome = pedidoProduto.produto.nome
    val valorPago = pedidoProduto.valorPago
    val quantidade = pedidoProduto.quantidade
    val comentario = pedidoProduto.comentario
}
