package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import java.math.BigDecimal
import java.time.OffsetDateTime

data class PagamentoDto(
    val id: Long? = null,
    val dataHora: OffsetDateTime? = null,
    val status: PagamentoStatus? = null,
    val qrCode: String? = null,
    val valorTotal: BigDecimal? = null
) {
    companion object {
        fun fromModel(pagamento: Pagamento) = PagamentoDto(
            pagamento.id,
            pagamento.dataHora,
            pagamento.status,
            pagamento.qrCode,
            pagamento.valorTotal
        )
    }

    fun toModel() = Pagamento(id, dataHora!!, status!!, qrCode!!, valorTotal!!)
}
