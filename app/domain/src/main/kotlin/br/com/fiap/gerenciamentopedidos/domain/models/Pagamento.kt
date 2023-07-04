package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import java.time.OffsetDateTime

data class Pagamento(
    val id: String?,
    val dataHora: OffsetDateTime,
    val status: PagamentoStatus
)
