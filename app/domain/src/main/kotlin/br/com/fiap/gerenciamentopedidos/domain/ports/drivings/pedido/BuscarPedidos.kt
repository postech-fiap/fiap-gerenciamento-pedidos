package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.pedido

import br.com.fiap.gerenciamentopedidos.domain.dtos.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.PedidoResponse

interface BuscarPedidos {

    fun executar(request: BuscarPedidosRequest): List<PedidoResponse>
}
