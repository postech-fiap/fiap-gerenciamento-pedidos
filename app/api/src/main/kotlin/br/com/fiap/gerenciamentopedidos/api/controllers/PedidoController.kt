package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pedidos")
class PedidoController(val buscarPedidosUseCase: BuscarPedidosUseCase) {

    @GetMapping
    fun buscarPedidos(buscarPedidosRequest: BuscarPedidosRequest) =
        ResponseEntity.ok().body(buscarPedidosUseCase.executar(buscarPedidosRequest))
}
