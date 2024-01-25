package br.com.fiap.gerenciamentopedidos.domain.interfaces.gateways

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto

fun interface ProducaoGateway {
    fun enviar(pedido: PedidoDto)
}