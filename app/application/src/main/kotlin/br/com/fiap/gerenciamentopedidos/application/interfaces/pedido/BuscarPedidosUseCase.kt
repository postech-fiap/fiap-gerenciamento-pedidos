package br.com.fiap.gerenciamentopedidos.application.interfaces.pedido

import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse

interface BuscarPedidosUseCase {

    fun executar(): List<PedidoResponse>

}
