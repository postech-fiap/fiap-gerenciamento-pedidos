package br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto

interface CadastrarClientePort {

    fun executar(cliente: ClienteDto): ClienteDto
}