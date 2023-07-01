package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import java.time.OffsetDateTime

data class PagamentoResponse(
    private val pagamento: Pagamento
) {

    val id: Long
    val dataHora: OffsetDateTime
    val status: String

    init {
        id = pagamento.id!!
        dataHora = pagamento.dataHora
        status = pagamento.status
    }

}
