package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pagamento

import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.MerchantOrders.Elements.Payments
import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.PagamentoCriado

interface FinalizarPagamentoUseCase {
    fun executar(pagamentoCriado: PagamentoCriado): Payments
}