package br.com.fiap.gerenciamentopedidos.application.pedido.usecases

import br.com.fiap.gerenciamentopedidos.application.pedido.interfaces.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.domain.interfaces.repositories.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

class BuscarUseCaseImpl(val pedidoRepository: PedidoRepository) : BuscarPedidosUseCase {

    override fun executar(buscarPedidosRequest: BuscarPedidosRequest): List<Pedido> {
        return pedidoRepository.buscarPedidos(
            buscarPedidosRequest.status,
            buscarPedidosRequest.dataInicial,
            buscarPedidosRequest.dataFinal
        )
    }

}
