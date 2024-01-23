package br.com.fiap.gerenciamentopedidos.api.adapters

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.PedidoAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.CadastrarPedidoUseCase

class PedidoAdapterImpl(
    private val buscarPedidosUseCase: BuscarPedidosUseCase,
    private val cadastrarPedidoUseCase: CadastrarPedidoUseCase,
    private val alterarStatusPedidoUseCase: AlterarStatusPedidoUseCase
) : PedidoAdapter {
    override fun buscarPedidos() = buscarPedidosUseCase.executar().map { PedidoResponse(it) }

    override fun cadastrarPedido(request: CadastrarPedidoRequest) =
        PedidoResponse(cadastrarPedidoUseCase.executar(request.clienteId, request.produtos!!.map { it.toModel() }))

    override fun alterarStatusPedido(request: AlterarStatusPedidoRequest) =
        alterarStatusPedidoUseCase.executar(request.referencia, request.status)
}
