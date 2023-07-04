package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import java.util.*

interface ClienteRepository {

    fun salvar(cliente: ClienteDto): ClienteDto
    fun buscarPorCpf(cpf: String): Optional<ClienteDto>
}