package br.com.fiap.gerenciamentopedidos.application.requests

class CadastrarPedidoRequest(
    val clienteId: Long, val produtos: List<CadastrarPedidoProdutoRequest>
)