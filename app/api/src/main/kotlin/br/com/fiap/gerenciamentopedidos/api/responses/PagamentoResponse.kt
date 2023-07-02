package br.com.fiap.gerenciamentopedidos.api.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import java.time.OffsetDateTime

data class PagamentoResponse(private val pagamento: PagamentoDto) {
    val id: Long
    val dataHora: OffsetDateTime
    val status: PagamentoStatus

    init {
        id = pagamento.id!!
        dataHora = pagamento.dataHora!!
        status = pagamento.status!!
    }
}
