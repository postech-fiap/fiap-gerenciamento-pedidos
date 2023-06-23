package br.com.fiap.gerenciamentopedidos.api.controllers

import br.com.fiap.gerenciamentopedidos.api.response.ClienteResponse
import br.com.fiap.gerenciamentopedidos.application.cadastro.interfaces.BuscarClientePorCpfUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clientes")
class ClienteController(
    val buscarClientePorCpfUseCase: BuscarClientePorCpfUseCase
) {

    @GetMapping("/{cpf}")
    fun buscarClientePorCpf(@PathVariable cpf: String): ResponseEntity<ClienteResponse> {
        val cliente = buscarClientePorCpfUseCase.executar(cpf)

        return ResponseEntity.status(HttpStatus.OK)
            .body(ClienteResponse.fromDomain(cliente))
    }
}
