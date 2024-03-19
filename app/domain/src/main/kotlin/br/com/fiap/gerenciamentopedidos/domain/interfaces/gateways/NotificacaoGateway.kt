package br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways

import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

interface NotificacaoGateway {
    fun notificarPedidoCriado(pedido: Pedido)
    fun notificarPedidoAlterado(pedido: Pedido)
}