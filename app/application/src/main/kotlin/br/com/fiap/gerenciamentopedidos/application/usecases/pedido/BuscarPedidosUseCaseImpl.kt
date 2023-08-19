package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository

class BuscarPedidosUseCaseImpl(private val pedidoRepository: PedidoRepository) : BuscarPedidosUseCase {

    override fun executar(): List<PedidoResponse> {
        return pedidoRepository.buscarPedidos().map { PedidoResponse(it) }
    }

}
