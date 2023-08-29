package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.facades.interfaces.PagamentoFacade
import br.com.fiap.gerenciamentopedidos.api.requests.PagamentoCriadoRequest
import br.com.fiap.gerenciamentopedidos.api.responses.DetalhePagamentoResponse
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pagamentos")
class PagamentoController(private val pagamentoController: PagamentoFacade) {

    @Operation(summary = "Finaliza o pagamento com status APROVADO ou REPROVADO")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Created",
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
    @PostMapping("/finalizar")
    fun finalizarPagamento(
        @RequestParam("data.id") dataId: String,
        @RequestParam("type") type: String,
        @RequestBody pagamentoCriadoRequest: PagamentoCriadoRequest
    ): DetalhePagamentoResponse {
        require("payment".equals(type, true)) { "O type deve ser payment "}

        return pagamentoController.finalizarPagamento(pagamentoCriadoRequest)
    }
}
