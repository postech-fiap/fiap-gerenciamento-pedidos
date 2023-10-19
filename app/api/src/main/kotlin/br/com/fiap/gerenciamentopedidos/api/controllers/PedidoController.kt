package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.PedidoAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.PedidoResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/pedidos")
class PedidoController(private val pedidoAdapter: PedidoAdapter) {
    @GetMapping
    fun buscarPedidos() = ResponseEntity.ok().body(pedidoAdapter.buscarPedidos())

    @PostMapping
    fun post(@RequestBody request: CadastrarPedidoRequest): ResponseEntity<PedidoResponse> {
        val pedido = pedidoAdapter.cadastrarPedido(request)
        return ResponseEntity.created(URI.create("/pedidos/${pedido.id}")).body(pedido)
    }

    @PatchMapping("/status")
    fun alterarStatusPedido(@RequestBody alterarStatusPedidoRequest: AlterarStatusPedidoRequest) =
        pedidoAdapter.alterarStatusPedido(alterarStatusPedidoRequest)

    @GetMapping("/{id}/pagamento/status")
    fun consultarStatusPagamento(@PathVariable id: Long) = pedidoAdapter.consultarStatusPagamento(id)
}
