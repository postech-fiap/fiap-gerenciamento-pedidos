package br.com.fiap.gerenciamentopedidos.domain.cadastro.interfaces.repositories

import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.ClienteDomain
import java.util.*

interface ClienteRepository {

    fun buscarPorCpf(cpf: String): Optional<ClienteDomain>
}