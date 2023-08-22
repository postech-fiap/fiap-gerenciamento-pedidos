package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus

interface AlterarStatusPedidoUseCase {
    fun executar(pedidoId: Long, status: PedidoStatus)
}