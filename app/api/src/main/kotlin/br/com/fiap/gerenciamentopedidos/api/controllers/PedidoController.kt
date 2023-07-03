package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.domain.dtos.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.pedido.BuscarPedidos
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pedidos")
class PedidoController(val buscarPedidosUseCase: BuscarPedidos) {

    @GetMapping
    fun buscarPedidos(buscarPedidosRequest: BuscarPedidosRequest) =
        ResponseEntity.status(HttpStatus.OK).body(buscarPedidosUseCase.executar(buscarPedidosRequest))
}
