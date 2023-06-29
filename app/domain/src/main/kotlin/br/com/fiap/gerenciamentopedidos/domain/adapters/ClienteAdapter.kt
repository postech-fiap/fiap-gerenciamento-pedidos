package br.com.fiap.gerenciamentopedidos.domain.adapters

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente
import java.util.*

interface ClienteAdapter {

    fun salvar(clienteDomain: Cliente): Cliente
    fun buscarPorCpf(cpf: String): Optional<Cliente>
}