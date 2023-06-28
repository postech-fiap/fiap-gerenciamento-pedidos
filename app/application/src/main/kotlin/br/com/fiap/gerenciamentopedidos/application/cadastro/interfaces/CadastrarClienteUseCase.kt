package br.com.fiap.gerenciamentopedidos.application.cadastro.interfaces

import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.ClienteDomain

interface CadastrarClienteUseCase {

    fun executar(clienteDomain: ClienteDomain): ClienteDomain
}