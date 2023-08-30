package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.AlterarStatusPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.ConsultarStatusPagamentoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.AlterarStatusPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.application.responses.PagamentoStatusResponse
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
class PedidoController(
    private val buscarPedidosUseCase: BuscarPedidosUseCase,
    private val cadastrarPedidoUseCase: CadastrarPedidoUseCase,
    private val alterarStatusPedido: AlterarStatusPedidoUseCase,
    private val consultarStatusPagamentoUseCase: ConsultarStatusPagamentoUseCase
) {
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
    fun buscarPedidos() = ResponseEntity.ok().body(buscarPedidosUseCase.executar())

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
        val pedido = cadastrarPedidoUseCase.executar(request)
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
    fun alterarStatusPedido(@RequestBody alterarStatusPedidoRequest: AlterarStatusPedidoRequest) {
        return alterarStatusPedido.executar(alterarStatusPedidoRequest)
    }

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
    fun consultarStatusPagamento(@PathVariable id: Long) = ResponseEntity.ok().body(consultarStatusPagamentoUseCase.executar(id))
}
