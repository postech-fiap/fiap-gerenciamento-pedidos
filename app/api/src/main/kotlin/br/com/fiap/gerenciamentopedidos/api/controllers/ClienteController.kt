package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.domain.dtos.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.BaseDeDadosException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoJaExisteException
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente.BuscarClientePorCpf
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente.CadastrarCliente
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clientes")
class ClienteController(
    private val cadastrarClienteUseCase: CadastrarCliente,
    private val buscarClientePorCpfUseCase: BuscarClientePorCpf
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
    fun cadastrarCliente(@RequestBody clienteRequest: CadastrarClienteRequest) =
        ResponseEntity.status(HttpStatus.CREATED).body(cadastrarClienteUseCase.executar(clienteRequest))


    @ApiOperation("Responsável por buscar um cliente")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok", response = ClienteResponse::class),
        ApiResponse(code = 404, message = "Not Found", response = RecursoNaoEncontradoException::class),
        ApiResponse(code = 500, message = "Internal Server Error", response = BaseDeDadosException::class),
        ApiResponse(code = 500, message = "Internal Server Error", response = Exception::class)
    )
    @GetMapping("$CPF_URI/{cpf}")
    fun buscarClientePorCpf(@PathVariable cpf: String) =
        ResponseEntity.status(HttpStatus.OK).body(buscarClientePorCpfUseCase.executar(cpf))
}
