package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

private const val COMPRA_LANCHONETE_DESCRIPTION = "Compra lanchonete"
private const val UNIT_MEASURE = "unit"

data class MercadoPagoOrdemDto(
    val externalReference: String,
    val totalAmount: BigDecimal,
    val items: List<MercadoPagoItensDto>,
    val title: String,
    val description: String,
    val expirationDate: String,
    val notificationUrl: String
) {
    data class MercadoPagoItensDto(
        val skuNumber: String,
        val category: String,
        val title: String,
        val description: String,
        val quantity: Long,
        val unitMeasure: String,
        val unitPrice: BigDecimal,
        val totalAmount: BigDecimal
    )

    companion object {
        fun fromDto(pedido: Pedido, webhookUrl: String) =
            MercadoPagoOrdemDto(
                externalReference = pedido.id.toString(),
                totalAmount = pedido.valorTotal!!,
                items = pedido.items.map {
                    MercadoPagoItensDto(
                        skuNumber = it.produto?.id.toString(),
                        category = it.produto?.categoria.toString(),
                        title = it.produto?.nome!!,
                        description = it.produto.descricao!!,
                        quantity = it.quantidade,
                        unitMeasure = UNIT_MEASURE,
                        unitPrice = it.produto.valor,
                        totalAmount = it.valorPago!!
                    )
                }.toList(),
                title = COMPRA_LANCHONETE_DESCRIPTION,
                description = COMPRA_LANCHONETE_DESCRIPTION,
                expirationDate = OffsetDateTime.now().plusHours(1).truncatedTo(ChronoUnit.MILLIS).toString(),
                notificationUrl = webhookUrl
            )
    }
}