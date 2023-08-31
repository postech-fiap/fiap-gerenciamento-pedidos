package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.time.OffsetDateTime

data class MercadoPagoResponseOrdemDto(
    val inStoreOrderId: String,
    val qrData: String
) {
    fun toModel(pedido: Pedido) = Pagamento(
        dataHora = OffsetDateTime.now(),
        status = PagamentoStatus.PENDENTE,
        qrCode = qrData,
        valorTotal = pedido.valorTotal!!
    )
}