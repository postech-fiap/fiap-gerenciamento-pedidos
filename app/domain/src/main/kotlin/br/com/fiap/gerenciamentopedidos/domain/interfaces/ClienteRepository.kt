package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
import java.util.*

interface ClienteRepository {

    fun salvar(cliente: Cliente): Cliente
    fun buscarPorCpf(cpf: String): Optional<Cliente>
    fun buscarPorId(id: Long): Optional<Cliente>
}
