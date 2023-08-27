package br.com.fiap.gerenciamentopedidos.api.facades

import br.com.fiap.gerenciamentopedidos.api.facades.interfaces.PedidoFacade
import br.com.fiap.gerenciamentopedidos.api.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.CadastrarPedidoUseCase


class PedidoFacadeImpl(
    private val buscarPedidosUseCase: BuscarPedidosUseCase,
    private val cadastrarPedidoUseCase: CadastrarPedidoUseCase,
    private val alterarStatusPedidoUseCase: AlterarStatusPedidoUseCase
) : PedidoFacade {
    override fun buscarPedidos() = buscarPedidosUseCase.executar().map { PedidoResponse(it) }

    override fun cadastrarPedido(request: CadastrarPedidoRequest) =
        PedidoResponse(cadastrarPedidoUseCase.executar(request.clienteId, request.produtos?.map { it.toModel() }!!))

    override fun alterarStatusPedido(request: AlterarStatusPedidoRequest) =
        alterarStatusPedidoUseCase.executar(request.pedidoId, request.status)
}
