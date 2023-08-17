package br.com.fiap.gerenciamentopedidos.application.requests

data class CadastrarPedidoRequest(
    val clienteId: Long? = null,
    val produtos: List<CadastrarPedidoProdutoRequest>? = null
) {
    init {
        require(produtos.isNullOrEmpty().not()) { "Deve ser informado ao menos um produto" }
    }

    val produtoIds get() = produtos?.map { it.produtoId } ?: emptyList()
}
