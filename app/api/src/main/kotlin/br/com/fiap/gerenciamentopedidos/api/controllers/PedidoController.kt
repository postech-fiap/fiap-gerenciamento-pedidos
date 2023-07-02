package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.application.interfaces.pedido.BuscarPedidosUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.BuscarPedidosRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.application.responses.PedidoResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoJaExisteException
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping("/pedidos")
class PedidoController(val buscarPedidosUseCase: BuscarPedidosUseCase) {


    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Ok",
            content = [ (Content(mediaType = "application/json",
                array = ArraySchema( schema = Schema(implementation = PedidoResponse::class))))])])
    @GetMapping
    fun buscarPedidos(buscarPedidosRequest: BuscarPedidosRequest): List<PedidoResponse> {
        val pedidosDomain = buscarPedidosUseCase.executar(buscarPedidosRequest)

        return pedidosDomain.stream()
            .map { pedidoDomain -> PedidoResponse(pedidoDomain) }
            .collect(Collectors.toList())
    }
}
