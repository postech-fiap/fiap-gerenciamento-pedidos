package br.com.fiap.gerenciamentopedidos.domain.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.BuscarPedidosUseCase

class BuscarPedidosUseCaseImpl(private val pedidoRepository: PedidoRepository) : BuscarPedidosUseCase {
    override fun executar() = pedidoRepository.buscarPedidos()
}
