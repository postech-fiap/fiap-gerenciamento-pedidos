package br.com.fiap.gerenciamentopedidos.api.responses

import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.MerchantOrders

class StatusPagamentoResponse(pagamento: MerchantOrders.Elements.Payment) {
    val status = pagamento.status
}
