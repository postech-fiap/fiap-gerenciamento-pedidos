package br.com.fiap.gerenciamentopedidos.application.interfaces.pedido

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse

interface CadastrarPedidoUseCase {
    fun executar(request: CadastrarPedidoRequest): PedidoResponse
}
