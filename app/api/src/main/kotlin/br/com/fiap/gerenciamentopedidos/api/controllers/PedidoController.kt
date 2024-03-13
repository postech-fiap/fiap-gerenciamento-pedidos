package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.PedidoAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.PedidoRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/pedidos")
class PedidoController(private val pedidoAdapter: PedidoAdapter, private val repo: PedidoRepository) {
    @PostMapping
    fun post(@RequestBody request: CadastrarPedidoRequest): ResponseEntity<PedidoResponse> {
        val pedido = pedidoAdapter.cadastrarPedido(request)
        return ResponseEntity.created(URI.create("/pedidos/${pedido.id}")).body(pedido)
    }

    //TODO: APAGAR
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = ResponseEntity.ok(repo.buscarPedidoPorId(id).get())
}
