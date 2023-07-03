package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.dtos.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.PedidoPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.pedido.BuscarPedidos

class BuscarPedidosImpl(private val pedidoPort: PedidoPort) : BuscarPedidos {
    override fun executar(request: BuscarPedidosRequest) =
        pedidoPort.buscarPedidos(request.status, request.dataInicial, request.dataFinal)
            .map { PedidoResponse(it.toModel()) }
}
