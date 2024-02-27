package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.PedidoAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.PedidoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/pedidos")
class PedidoController(private val pedidoAdapter: PedidoAdapter) {
    @PostMapping
    fun post(@RequestBody request: CadastrarPedidoRequest): ResponseEntity<PedidoResponse> {
        val pedido = pedidoAdapter.cadastrarPedido(request)
        return ResponseEntity.created(URI.create("/pedidos/${pedido.id}")).body(pedido)
    }
}
