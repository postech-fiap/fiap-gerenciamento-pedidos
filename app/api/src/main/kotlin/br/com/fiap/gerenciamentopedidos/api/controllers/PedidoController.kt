package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.facades.interfaces.PedidoFacade
import br.com.fiap.gerenciamentopedidos.api.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.PagamentoStatusResponse
import br.com.fiap.gerenciamentopedidos.api.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/pedidos")
class PedidoController(private val pedidoFacade: PedidoFacade) {
    @Operation(summary = "Busca pedidos por status")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200", description = "Ok",
            content = [(Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = PedidoResponse::class))
            )
                    )]
        ), ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = BaseDeDadosException::class)
            )]
        )]
    )
    @GetMapping
    fun buscarPedidos() = ResponseEntity.ok().body(pedidoFacade.buscarPedidos())

    @Operation(summary = "Cadastrar um pedido")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Created",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PedidoResponse::class)
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BaseDeDadosException::class)
                )]
            )]
    )
    @PostMapping
    fun post(@RequestBody request: CadastrarPedidoRequest): ResponseEntity<PedidoResponse> {
        val pedido = pedidoFacade.cadastrarPedido(request)
        return ResponseEntity.created(URI.create("/pedidos/${pedido.id}")).body(pedido)
    }

    @Operation(summary = "Alterar status de um pedido")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Ok",
                content = [(Content(
                    mediaType = "application/json"
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BaseDeDadosException::class)
                )]
            )]
    )
    @PatchMapping("/status")
    fun alterarStatusPedido(@RequestBody alterarStatusPedidoRequest: AlterarStatusPedidoRequest) =
        pedidoFacade.alterarStatusPedido(alterarStatusPedidoRequest)

    @Operation(summary = "Consultar status do pagamento de um pedido")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Ok",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PagamentoStatusResponse::class)
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BaseDeDadosException::class)
                )]
            )]
    )
    @GetMapping("/{id}/pagamento/status")
    fun consultarStatusPagamento(@PathVariable id: Long) = pedidoFacade.consultarStatusPagamento(id)
}
