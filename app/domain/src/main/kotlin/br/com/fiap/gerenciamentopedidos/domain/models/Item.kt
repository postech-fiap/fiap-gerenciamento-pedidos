package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.interfaces.Model
import java.math.BigDecimal

data class Item(
    val id: Long? = null,
    val quantidade: Long,
    val comentario: String? = null,
    val produto: Produto? = null,
    val valorPago: BigDecimal? = null,
) : Model {
    override fun valid(): Item {
        require(quantidade > 0) { "A quantidade deve ser maior que zero" }
        require(produto != null) { "O produto deve ser informado" }
        require(valorPago != null) { "O valor pago do pedido deve ser informado" }
        return this
    }
}
