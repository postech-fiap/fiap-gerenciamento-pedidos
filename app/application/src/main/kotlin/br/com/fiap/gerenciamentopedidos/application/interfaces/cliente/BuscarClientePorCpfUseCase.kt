package br.com.fiap.gerenciamentopedidos.application.interfaces.cliente

import br.com.fiap.gerenciamentopedidos.application.responses.ClienteResponse

interface BuscarClientePorCpfUseCase {

    fun executar(cpf: String): ClienteResponse
}