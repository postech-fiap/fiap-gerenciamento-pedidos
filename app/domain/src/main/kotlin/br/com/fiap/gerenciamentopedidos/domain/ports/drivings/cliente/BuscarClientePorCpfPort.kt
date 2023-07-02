package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto

interface BuscarClientePorCpfPort {

    fun executar(cpf: String): ClienteDto
}