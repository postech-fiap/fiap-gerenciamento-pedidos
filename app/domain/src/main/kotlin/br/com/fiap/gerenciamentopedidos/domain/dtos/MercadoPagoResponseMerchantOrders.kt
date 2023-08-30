package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.MerchantOrders
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.MerchantOrders.Elements
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.MerchantOrders.Elements.Payment
import java.math.BigDecimal

data class MercadoPagoResponseMerchantOrders(
        val elements: List<ElementResponse>? = emptyList()
) {
    data class ElementResponse(
            val id: Long,
            val status: String,
            val externalReference: String,
            val payments: List<PaymentResponse>? = emptyList(),
    ) {
        data class PaymentResponse(
                val id: Long,
                val transactionAmount: BigDecimal,
                val totalPaidAmount: BigDecimal,
                val status: String,
                val statusDetail: String,
                val dateApproved: String,
                val dateCreated: String,
                val lastModified: String,
                val amountRefunded: BigDecimal,
        ) {

            fun toModel() = Payment(
                    id = id,
                    transactionAmount = transactionAmount,
                    totalPaidAmount = totalPaidAmount,
                    status = status,
                    statusDetail = statusDetail,
                    dateApproved = dateApproved,
                    dateCreated = dateCreated,
                    lastModified = lastModified,
                    amountRefunded = amountRefunded
            )
        }

        fun toModel() = Elements(
                id = id,
                status = status,
                externalReference = externalReference,
                payments = payments?.map { paymentResponse -> paymentResponse.toModel() }
        )
    }

    fun toModel() = MerchantOrders(
            elements = elements?.map { elementResponse -> elementResponse.toModel() }
    )

}