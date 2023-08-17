package br.com.fiap.gerenciamentopedidos.application.usecases.pedido

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.GerarNumeroPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository

class GerarNumeroPedidoUseCaseImpl(private val pedidoRepository: PedidoRepository) : GerarNumeroPedidoUseCase {
    override fun executar() = (pedidoRepository.obterUltimoNumeroPedidoDoDia().toInt() + 1).toString()
}
