package br.com.fiap.gerenciamentopedidos.application.cadastro.interfaces

import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.ClienteDomain

interface BuscarClientePorCpfUseCase {

    fun executar(cpf: String): ClienteDomain
}