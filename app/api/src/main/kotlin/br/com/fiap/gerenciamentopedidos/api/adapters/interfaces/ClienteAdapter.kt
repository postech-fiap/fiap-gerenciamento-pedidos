package br.com.fiap.gerenciamentopedidos.api.adapters.interfaces

import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.api.responses.ClienteResponse

interface ClienteAdapter {
    fun cadastrarCliente(request: CadastrarClienteRequest): ClienteResponse

    fun buscarClientePorCpf(cpf: String): ClienteResponse
}
