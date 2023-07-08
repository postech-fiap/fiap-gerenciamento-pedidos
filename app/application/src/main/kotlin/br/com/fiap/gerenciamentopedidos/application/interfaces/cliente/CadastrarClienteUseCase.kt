package br.com.fiap.gerenciamentopedidos.application.interfaces.cliente

import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ClienteResponse

interface CadastrarClienteUseCase {

    fun executar(request: CadastrarClienteRequest): ClienteResponse
}