package br.com.fiap.gerenciamentopedidos.application.interfaces

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ClienteResponse

interface ClienteController {
    fun cadastrarCliente(request: CadastrarClienteRequest): ClienteResponse

    fun buscarClientePorCpf(cpf: String): ClienteResponse
}
