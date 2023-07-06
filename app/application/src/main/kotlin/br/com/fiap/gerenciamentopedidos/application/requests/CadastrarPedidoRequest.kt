package br.com.fiap.gerenciamentopedidos.application.requests

data class CadastrarPedidoRequest(
    val clienteId: Long? = null,
    val produtos: List<CadastrarPedidoProdutoRequest>? = null
){
    init {
        require(produtos != null && produtos.isNotEmpty()) { "Deve ser informado ao menos um produto" }
    }
}
