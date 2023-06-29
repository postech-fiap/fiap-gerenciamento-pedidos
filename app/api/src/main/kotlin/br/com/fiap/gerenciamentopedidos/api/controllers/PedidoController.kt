package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping("/pedidos")
class PedidoController(val buscarPedidosUseCase: BuscarPedidosUseCase) {

    @GetMapping
    fun buscarPedidos(buscarPedidosRequest: BuscarPedidosRequest): List<PedidoResponse> {
        val pedidosDomain = buscarPedidosUseCase.executar(buscarPedidosRequest)

        return pedidosDomain.stream()
            .map { pedidoDomain -> PedidoResponse(pedidoDomain) }
            .collect(Collectors.toList())
    }
}
