package br.com.fiap.gerenciamentopedidos.api.adapters

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.PedidoAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.CadastrarPedidoUseCase
import java.util.*

class PedidoAdapterImpl(
    private val cadastrarPedidoUseCase: CadastrarPedidoUseCase,
    private val alterarStatusPedidoUseCase: AlterarStatusPedidoUseCase
) : PedidoAdapter {
    override fun cadastrarPedido(request: CadastrarPedidoRequest) =
        PedidoResponse(cadastrarPedidoUseCase.executar(request.clienteId, request.produtos!!.map { it.toModel() }))

    override fun alterarStatusPedido(request: AlterarStatusPedidoRequest) =
        alterarStatusPedidoUseCase.executar(
            UUID.fromString(request.referenciaPedido),
            PagamentoStatus.valueOf(request.statusPagamento!!)
        )
}
