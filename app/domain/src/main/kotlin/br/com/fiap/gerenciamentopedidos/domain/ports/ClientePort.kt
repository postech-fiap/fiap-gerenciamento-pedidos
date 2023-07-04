package br.com.fiap.gerenciamentopedidos.domain.ports

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import java.util.*

interface ClientePort {

    fun salvar(cliente: ClienteDto): ClienteDto
    fun buscarPorCpf(cpf: String): Optional<ClienteDto>
}