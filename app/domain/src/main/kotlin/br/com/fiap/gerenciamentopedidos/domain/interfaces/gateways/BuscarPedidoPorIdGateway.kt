package br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways

import br.com.fiap.gerenciamentopedidos.domain.models.mercadoPago.DetalhePagamento

interface BuscarPedidoPorIdGateway {
    fun executar(id: String): DetalhePagamento
}
