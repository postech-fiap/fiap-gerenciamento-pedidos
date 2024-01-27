package br.com.fiap.gerenciamentopedidos.api.requests

data class CadastrarPedidoRequest(
    val clienteId: String? = null,
    val produtos: List<CadastrarPedidoProdutoRequest>? = null
) {
    init {
        require(produtos.isNullOrEmpty().not()) { "Deve ser informado ao menos um produto" }
    }
}
