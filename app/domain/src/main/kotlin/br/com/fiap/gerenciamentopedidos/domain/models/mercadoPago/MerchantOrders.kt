package br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.math.BigDecimal

private const val STATUS_APROVADO = "approved"

data class MerchantOrders(
        val elements: List<Elements>? = emptyList()
) {
    data class Elements(
            val id: Long,
            val status: String,
            val externalReferences: String,
            val payments: List<Payments>? = emptyList()
    ) {
        data class Payments(
                val id: Long,
                val transactionAmount: BigDecimal,
                val totalPaidAmount: BigDecimal,
                val status: String,
                val statusDetail: String,
                val operationType: String,
                val dateApproved: String,
                val dateCreated: String,
                val lastModified: String,
                val amountRefunded: String
        ) {

            fun calcularStatusPagamentoPedido() =
                    if (!STATUS_APROVADO.equals(this.status, true)) {
                        Pair(PagamentoStatus.REPROVADO, PedidoStatus.PENDENTE)
                    } else {
                        Pair(PagamentoStatus.APROVADO, PedidoStatus.RECEBIDO)
                    }
        }
    }

    fun obterUltimoPagamento() =
            this.elements?.get(0)
                    ?.payments?.get(0)

    fun obterIdDoPedido() =
            this.elements?.get(0)
                    ?.externalReferences
}
