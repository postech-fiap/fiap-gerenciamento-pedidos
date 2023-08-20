package br.com.fiap.gerenciamentopedidos.application.interfaces.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus

interface AlterarStatusPedidoUseCase {
    fun executar(pedidoId: Long, status: PedidoStatus)
}