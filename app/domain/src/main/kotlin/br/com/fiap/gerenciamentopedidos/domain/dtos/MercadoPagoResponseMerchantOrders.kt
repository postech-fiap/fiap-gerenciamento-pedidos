package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.MerchantOrders
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.MerchantOrders.Elements
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.MerchantOrders.Elements.Payments
import java.math.BigDecimal

data class MercadoPagoResponseMerchantOrders(
        private val elementsResponses: List<ElementsResponse>? = emptyList()
) {
    data class ElementsResponse(
            private val id: Long,
            private val status: String,
            private val externalReferences: String,
            private val paymentsResponses: List<PaymentsResponse>? = emptyList(),
    ) {
        data class PaymentsResponse(
                private val id: Long,
                private val transactionAmount: BigDecimal,
                private val totalPaidAmount: BigDecimal,
                private val status: String,
                private val statusDetail: String,
                private val operationType: String,
                private val dateApproved: String,
                private val dateCreated: String,
                private val lastModified: String,
                private val amountRefunded: String,
        ) {

            fun toModel() = Payments(
                    id = id,
                    transactionAmount = transactionAmount,
                    totalPaidAmount = totalPaidAmount,
                    status = status,
                    statusDetail = statusDetail,
                    operationType = operationType,
                    dateApproved = dateApproved,
                    dateCreated = dateCreated,
                    lastModified = lastModified,
                    amountRefunded = amountRefunded
            )
        }

        fun toModel() = Elements(
                id = id,
                status = status,
                externalReferences = externalReferences,
                payments = paymentsResponses?.map { paymentResponse -> paymentResponse.toModel() }
        )
    }

    fun toModel() = MerchantOrders(
            elements = elementsResponses?.map { elementResponse -> elementResponse.toModel() }
    )

}