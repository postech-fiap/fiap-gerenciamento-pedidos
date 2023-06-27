package br.com.fiap.gerenciamentopedidos.application.pedido.interfaces

import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

interface BuscarPedidosUseCase {

    fun executar(buscarPedidosRequest: BuscarPedidosRequest): List<Pedido>

}
