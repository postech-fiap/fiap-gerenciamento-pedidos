package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.application.interfaces.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.application.interfaces.cliente.CadastrarClienteUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoJaExisteException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clientes")
class ClienteController(
    val cadastrarClienteUseCase: CadastrarClienteUseCase,
    val buscarClientePorCpfUseCase: BuscarClientePorCpfUseCase
) {

    companion object {
        const val CPF_URI = "/cpf"
    }

    @ApiOperation("Responsável por cadastrar um cliente")
    @ApiResponses(
        ApiResponse(code = 201, message = "Created", response = ClienteResponse::class),
        ApiResponse(code = 409, message = "Conflict", response = RecursoJaExisteException::class),
        ApiResponse(code = 400, message = "Bad Request", response = IllegalArgumentException::class),
        ApiResponse(code = 500, message = "Internal Server Error", response = BaseDeDadosException::class),
        ApiResponse(code = 500, message = "Internal Server Error", response = Exception::class)
    )
    @PostMapping(CPF_URI)
    fun cadastrarCliente(@RequestBody clienteRequest: CadastrarClienteRequest): ResponseEntity<ClienteResponse> {
        val cliente = cadastrarClienteUseCase.executar(clienteRequest)

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(cliente)
    }

    @ApiOperation("Responsável por buscar um cliente")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok", response = ClienteResponse::class),
        ApiResponse(code = 404, message = "Not Found", response = RecursoNaoEncontradoException::class),
        ApiResponse(code = 500, message = "Internal Server Error", response = BaseDeDadosException::class),
        ApiResponse(code = 500, message = "Internal Server Error", response = Exception::class)
    )
    @GetMapping("$CPF_URI/{cpf}")
    fun buscarClientePorCpf(@PathVariable cpf: String): ResponseEntity<ClienteResponse> {
        val cliente = buscarClientePorCpfUseCase.executar(cpf)

        return ResponseEntity.status(HttpStatus.OK)
            .body(cliente)
    }
}
