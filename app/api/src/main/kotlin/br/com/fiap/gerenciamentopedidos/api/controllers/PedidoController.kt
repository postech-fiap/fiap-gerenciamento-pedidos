package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.CadastrarPedidoUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarPedidoRequest
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarProdutoRequest
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.application.responses.ProdutoResponse
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/pedidos")
class PedidoController(
    private val buscarPedidosUseCase: BuscarPedidosUseCase,
    private val cadastrarPedidoUseCase: CadastrarPedidoUseCase
    ) {

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Ok",
            content = [ (Content(mediaType = "application/json",
                array = ArraySchema( schema = Schema(implementation = PedidoResponse::class))))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
        content = [ Content(mediaType = "application/json",
            schema = Schema(implementation = BaseDeDadosException::class))])])
    @GetMapping
    fun buscarPedidos(buscarPedidosRequest: BuscarPedidosRequest) =
        ResponseEntity.ok().body(buscarPedidosUseCase.executar(buscarPedidosRequest))

    @Operation(summary = "Cadastrar um pedido")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Created",
            content = [ (Content(mediaType = "application/json",
                schema = Schema(implementation = PedidoResponse::class)))]),
        ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = [ Content(mediaType = "application/json",
                schema = Schema(implementation = BaseDeDadosException::class))])])
    @PostMapping
    fun post(@RequestBody request: CadastrarPedidoRequest): ResponseEntity<PedidoResponse> {
        val pedido = cadastrarPedidoUseCase.executar(request)
        return ResponseEntity.created(URI.create("/pedidos/${pedido.id}")).body(pedido)
    }
}
