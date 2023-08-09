package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import java.math.BigDecimal
import java.time.OffsetDateTime

data class Pagamento(
    val id: Long? = null,
    val dataHora: OffsetDateTime,
    val status: PagamentoStatus,
    val qrCode: String,
    val valorTotal: BigDecimal
)
