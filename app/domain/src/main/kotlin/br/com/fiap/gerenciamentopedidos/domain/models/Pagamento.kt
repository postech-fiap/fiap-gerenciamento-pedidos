package br.com.fiap.gerenciamentopedidos.domain.models

import java.time.OffsetDateTime

data class Pagamento(
    val id: Long? = null,
    val dataHora: OffsetDateTime,
    val status: String,
    val pedido: Pedido?
)
