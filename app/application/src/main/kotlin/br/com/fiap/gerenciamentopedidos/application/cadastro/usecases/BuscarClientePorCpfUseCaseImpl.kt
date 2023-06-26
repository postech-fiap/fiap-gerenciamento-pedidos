package br.com.fiap.gerenciamentopedidos.application.cadastro.usecases

import br.com.fiap.gerenciamentopedidos.application.cadastro.interfaces.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.cadastro.interfaces.repositories.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.ClienteDomain

class BuscarClientePorCpfUseCaseImpl(val clienteRepository: ClienteRepository) : BuscarClientePorCpfUseCase {

    override fun executar(cpf: String): ClienteDomain {
        return clienteRepository.buscarPorCpf(cpf)
            .orElseThrow {
                RecursoNaoEncontradoException(
                    String.format("CPF %s não encontrado", cpf)
                )
            }
    }
}
