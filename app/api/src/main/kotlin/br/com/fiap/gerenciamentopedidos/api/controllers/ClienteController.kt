package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.requests.ClienteRequest
import br.com.fiap.gerenciamentopedidos.api.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente.BuscarClientePorCpfPort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente.CadastrarClientePort
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoJaExisteException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clientes")
class ClienteController(
    private val cadastrarClienteUseCase: CadastrarClientePort,
    private val buscarClientePorCpfUseCase: BuscarClientePorCpfPort
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
    fun cadastrarCliente(@RequestBody clienteRequest: ClienteRequest): ResponseEntity<ClienteResponse> {
        val cliente = cadastrarClienteUseCase.executar(clienteRequest.toDto())

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ClienteResponse.fromDto(cliente))
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
            .body(ClienteResponse.fromDto(cliente))
    }
}
