package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.ClienteAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarClienteRequest
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

const val CPF_URI = "/cpf"

@RestController
@RequestMapping("/clientes")
class ClienteController(private val clienteAdapter: ClienteAdapter) {

    @PostMapping(CPF_URI)
    fun cadastrarCliente(@RequestBody request: CadastrarClienteRequest) =
        ResponseEntity.status(HttpStatus.CREATED).body(clienteAdapter.cadastrarCliente(request))

    @GetMapping("$CPF_URI/{cpf}")
    fun buscarClientePorCpf(
        @PathVariable("cpf")
        @Parameter(name = "cpf", description = "CPF do cliente", example = "43253353425")
        cpf: String
    ) = ResponseEntity.status(HttpStatus.OK).body(clienteAdapter.buscarClientePorCpf(cpf))
}
