package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.api.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.pedido.BuscarPedidosPort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping("/pedidos")
class PedidoController(val buscarPedidosUseCase: BuscarPedidosPort) {

    @GetMapping
    fun buscarPedidos(buscarPedidosRequest: BuscarPedidosRequest): List<PedidoResponse> {
        val pedidosDomain = buscarPedidosUseCase.executar(
            buscarPedidosRequest.status,
            buscarPedidosRequest.dataInicial,
            buscarPedidosRequest.dataFinal
        )

        return pedidosDomain.stream()
            .map { pedidoDomain -> PedidoResponse(pedidoDomain) }
            .collect(Collectors.toList())
    }
}
