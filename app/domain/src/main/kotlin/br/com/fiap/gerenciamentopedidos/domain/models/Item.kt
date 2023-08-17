package br.com.fiap.gerenciamentopedidos.domain.models

import java.math.BigDecimal

data class Item(
    val id: Long? = null,
    val quantidade: Long,
    val comentario: String? = null,
    val produto: Produto? = null,
    val valorPago: BigDecimal? = null,
) {
    init {
        require(quantidade > 0) { "A quantidade deve ser maior que zero" }
        require(produto != null) { "O produto deve ser informado" }
    }
}
