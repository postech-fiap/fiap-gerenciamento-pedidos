package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.CadastrarClienteUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoJaExisteException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.infrastructure.exceptions.BaseDeDadosException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clientes")
class ClienteController(
    private val cadastrarClienteUseCase: CadastrarClienteUseCase,
    private val buscarClientePorCpfUseCase: BuscarClientePorCpfUseCase
) {
    companion object {
        const val CPF_URI = "/cpf"
    }

    @Operation(summary = "Responsável por cadastrar um cliente")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Created",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ClienteResponse::class)
                ))]
            ),
            ApiResponse(
                responseCode = "409", description = "Conflict",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RecursoJaExisteException::class)
                ))]
            ),
            ApiResponse(
                responseCode = "400", description = "Bad Request",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = IllegalArgumentException::class)
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BaseDeDadosException::class)
                )]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Exception::class)
                )]
            )]
    )
    @PostMapping(CPF_URI)
    fun cadastrarCliente(@RequestBody request: CadastrarClienteRequest) =
        ResponseEntity.status(HttpStatus.CREATED).body(cadastrarClienteUseCase.executar(request))

    @Operation(summary = "Responsável por buscar um cliente")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Ok",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ClienteResponse::class)
                ))]
            ),
            ApiResponse(
                responseCode = "404", description = "Not Found",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RecursoNaoEncontradoException::class)
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BaseDeDadosException::class)
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Exception::class)
                )]
            )]
    )
    @GetMapping("$CPF_URI/{cpf}")
    fun buscarClientePorCpf(
        @PathVariable("cpf")
        @Parameter(name = "cpf", description = "CPF do cliente", example = "43253353425")
        cpf: String
    ) = ResponseEntity.status(HttpStatus.OK)
        .body(buscarClientePorCpfUseCase.executar(cpf))
}
