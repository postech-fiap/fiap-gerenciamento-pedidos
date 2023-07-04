package br.com.fiap.gerenciamentopedidos.domain.models

import java.math.BigDecimal

data class PedidoProduto(
    val id: Long? = null,
    val produto: Produto?,
    val valorPago: BigDecimal,
    val quantidade: Int,
    val comentario: String?
)