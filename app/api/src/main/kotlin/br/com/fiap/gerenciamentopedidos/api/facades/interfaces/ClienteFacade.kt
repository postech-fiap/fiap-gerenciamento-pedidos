package br.com.fiap.gerenciamentopedidos.api.facades.interfaces

import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.api.responses.ClienteResponse

interface ClienteFacade {
    fun cadastrarCliente(request: CadastrarClienteRequest): ClienteResponse

    fun buscarClientePorCpf(cpf: String): ClienteResponse
}
