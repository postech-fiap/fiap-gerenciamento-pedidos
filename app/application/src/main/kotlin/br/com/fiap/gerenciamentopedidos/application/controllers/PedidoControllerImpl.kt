package br.com.fiap.gerenciamentopedidos.application.controllers

import br.com.fiap.gerenciamentopedidos.application.interfaces.PedidoController
import br.com.fiap.gerenciamentopedidos.application.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.CadastrarPedidoUseCase


class PedidoControllerImpl(
    private val buscarPedidosUseCase: BuscarPedidosUseCase,
    private val cadastrarPedidoUseCase: CadastrarPedidoUseCase,
    private val alterarStatusPedidoUseCase: AlterarStatusPedidoUseCase
) : PedidoController {
    override fun buscarPedidos(request: BuscarPedidosRequest) =
        buscarPedidosUseCase.executar(request.status, request.dataInicial, request.dataFinal).map { PedidoResponse(it) }

    override fun cadastrarPedido(request: CadastrarPedidoRequest) =
        PedidoResponse(cadastrarPedidoUseCase.executar(request.clienteId, request.produtos.map { it.toModel() }))

    override fun alterarStatusPedido(request: AlterarStatusPedidoRequest) =
        alterarStatusPedidoUseCase.executar(request.pedidoId, request.status)
}
