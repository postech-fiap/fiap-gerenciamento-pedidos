package br.com.fiap.gerenciamentopedidos.domain.models

data class PedidoProduto(
    val id: Long? = null,
    val produtoId: Long?,
    val quantidade: Int,
    val comentario: String? = null
) {
    fun getValorTotal() = 100.0
}