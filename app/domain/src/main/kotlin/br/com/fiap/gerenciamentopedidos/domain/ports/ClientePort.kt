package br.com.fiap.gerenciamentopedidos.domain.ports

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
import java.util.*

interface ClientePort {

    fun salvar(clienteDomain: Cliente): Cliente
    fun buscarPorCpf(cpf: String): Optional<Cliente>
}