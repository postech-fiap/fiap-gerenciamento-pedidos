package br.com.fiap.gerenciamentopedidos.domain.models

data class PedidoProduto(
    val id: Long? = null,
    val produto: Produto,
    val quantidade: Int,
    val comentario: String? = null
) {
    fun getValorTotal() = produto.valor.toBigDecimal().multiply(quantidade.toBigDecimal()).toDouble()
}