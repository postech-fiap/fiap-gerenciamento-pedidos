package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente

import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ClienteResponse

interface BuscarClientePorCpf {

    fun executar(cpf: String): ClienteResponse
}