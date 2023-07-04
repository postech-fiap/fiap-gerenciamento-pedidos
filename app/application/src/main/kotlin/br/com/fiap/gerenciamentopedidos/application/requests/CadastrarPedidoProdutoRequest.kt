package br.com.fiap.gerenciamentopedidos.application.requests

data class CadastrarPedidoProdutoRequest(
    val produtoId: Long? = null,
    val quantidade: Int? = null,
    val comentario: String? = null,
)