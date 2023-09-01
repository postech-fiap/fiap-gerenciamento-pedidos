package br.com.fiap.gerenciamentopedidos.api.facades.interfaces

import br.com.fiap.gerenciamentopedidos.api.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.PagamentoStatusResponse
import br.com.fiap.gerenciamentopedidos.api.responses.PedidoResponse

interface PedidoFacade {
    fun buscarPedidos(): List<PedidoResponse>

    fun cadastrarPedido(request: CadastrarPedidoRequest): PedidoResponse

    fun alterarStatusPedido(request: AlterarStatusPedidoRequest)

    fun consultarStatusPagamento(id: Long) : PagamentoStatusResponse
}
