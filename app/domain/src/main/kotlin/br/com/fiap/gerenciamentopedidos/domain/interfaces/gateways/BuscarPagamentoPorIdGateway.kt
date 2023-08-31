package br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways

import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.MerchantOrders

interface BuscarPagamentoPorIdGateway {
    fun executar(id: String): MerchantOrders
}
