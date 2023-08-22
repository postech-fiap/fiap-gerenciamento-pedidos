package br.com.fiap.gerenciamentopedidos.application.controllers

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.CadastrarClienteUseCase
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clientes")
class ClienteController(
    private val cadastrarClienteUseCase: CadastrarClienteUseCase,
    private val buscarClientePorCpfUseCase: BuscarClientePorCpfUseCase
) {

    fun cadastrarCliente(@RequestBody request: CadastrarClienteRequest) =
        ResponseEntity.status(HttpStatus.CREATED).body(cadastrarClienteUseCase.executar(request))

    fun buscarClientePorCpf(
        @PathVariable("cpf")
        @Parameter(name = "cpf", description = "CPF do cliente", example = "43253353425")
        cpf: String
    ) = ResponseEntity.status(HttpStatus.OK)
        .body(buscarClientePorCpfUseCase.executar(cpf))
}
