package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente

import br.com.fiap.gerenciamentopedidos.domain.dtos.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ClienteResponse

interface CadastrarCliente {

    fun executar(request: CadastrarClienteRequest): ClienteResponse
}