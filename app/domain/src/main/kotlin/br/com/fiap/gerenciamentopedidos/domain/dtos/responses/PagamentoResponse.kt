package br.com.fiap.gerenciamentopedidos.domain.dtos.responses

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import java.time.OffsetDateTime

class PagamentoResponse(pagamento: Pagamento) {
    val id: Long
    val dataHora: OffsetDateTime
    val status: PagamentoStatus

    init {
        id = pagamento.id!!
        dataHora = pagamento.dataHora
        status = pagamento.status
    }
}
