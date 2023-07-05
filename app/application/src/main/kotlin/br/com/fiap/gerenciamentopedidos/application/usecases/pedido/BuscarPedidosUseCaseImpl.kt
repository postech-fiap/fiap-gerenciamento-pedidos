package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository

class BuscarPedidosUseCaseImpl(private val pedidoPort: PedidoRepository) : BuscarPedidosUseCase {

    override fun executar(buscarPedidosRequest: BuscarPedidosRequest): List<PedidoResponse> {
        return pedidoPort.buscarPedidos(
            buscarPedidosRequest.status,
            buscarPedidosRequest.dataInicial,
            buscarPedidosRequest.dataFinal
        ).map { PedidoResponse(it) }
    }

}