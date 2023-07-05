package br.com.fiap.gerenciamentopedidos.application.requests

data class CadastrarPedidoRequest(
    val clienteId: Long? = null,
    val produtos: List<CadastrarPedidoProdutoRequest>? = null
)
