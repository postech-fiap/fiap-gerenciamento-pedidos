package br.com.fiap.gerenciamentopedidos.domain.models

data class PedidoProduto(
    val id: Long? = null,
    val produtoId: Long?,
    val quantidade: Long,
    val comentario: String? = null,
    val produto: Produto? = null
)
