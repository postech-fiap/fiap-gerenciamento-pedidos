package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.domain.ports.PedidoPort
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

class BuscarUseCaseImpl(val pedidoPort: PedidoPort) : BuscarPedidosUseCase {

    override fun executar(buscarPedidosRequest: BuscarPedidosRequest): List<Pedido> {
        return pedidoPort.buscarPedidos(
            buscarPedidosRequest.status,
            buscarPedidosRequest.dataInicial,
            buscarPedidosRequest.dataFinal
        )
    }

}
