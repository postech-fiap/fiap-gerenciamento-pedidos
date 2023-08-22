package br.com.fiap.gerenciamentopedidos.application.interfaces

import br.com.fiap.gerenciamentopedidos.application.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse

interface PedidoController {
    fun buscarPedidos(request: BuscarPedidosRequest): List<PedidoResponse>

    fun cadastrarPedido(request: CadastrarPedidoRequest): PedidoResponse

    fun alterarStatusPedido(request: AlterarStatusPedidoRequest)
}
