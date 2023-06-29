package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.domain.adapters.PedidoAdapter
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

class BuscarUseCaseImpl(val pedidoAdapter: PedidoAdapter) : BuscarPedidosUseCase {

    override fun executar(buscarPedidosRequest: BuscarPedidosRequest): List<Pedido> {
        return pedidoAdapter.buscarPedidos(
            buscarPedidosRequest.status,
            buscarPedidosRequest.dataInicial,
            buscarPedidosRequest.dataFinal
        )
    }

}
