package br.com.fiap.gerenciamentopedidos.application.controllers

import br.com.fiap.gerenciamentopedidos.application.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido.CadastrarPedidoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/pedidos")
class PedidoController(
    private val buscarPedidosUseCase: BuscarPedidosUseCase,
    private val cadastrarPedidoUseCase: CadastrarPedidoUseCase,
    private val alterarStatusPedido: AlterarStatusPedidoUseCase
) {
    fun buscarPedidos(buscarPedidosRequest: BuscarPedidosRequest) =
        ResponseEntity.ok().body(buscarPedidosUseCase.executar(buscarPedidosRequest))

    fun post(@RequestBody request: CadastrarPedidoRequest): ResponseEntity<PedidoResponse> {
        val pedido = cadastrarPedidoUseCase.executar(request)
        return ResponseEntity.created(URI.create("/pedidos/${pedido.id}")).body(pedido)
    }

    fun alterarStatusPedido(@RequestBody alterarStatusPedidoRequest: AlterarStatusPedidoRequest) {
        return alterarStatusPedido.executar(alterarStatusPedidoRequest)
    }
}
