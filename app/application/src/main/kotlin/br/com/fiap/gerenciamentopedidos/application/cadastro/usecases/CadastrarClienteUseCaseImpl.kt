package br.com.fiap.gerenciamentopedidos.application.cadastro.usecases

import br.com.fiap.gerenciamentopedidos.application.cadastro.interfaces.CadastrarClienteUseCase
import br.com.fiap.gerenciamentopedidos.domain.cadastro.exceptions.RecursoJaExisteException
import br.com.fiap.gerenciamentopedidos.domain.cadastro.interfaces.repositories.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.cadastro.models.ClienteDomain

class CadastrarClienteUseCaseImpl(val clienteRepository: ClienteRepository) : CadastrarClienteUseCase {

    override fun executar(clienteDomain: ClienteDomain): ClienteDomain {
        clienteRepository.buscarPorCpf(clienteDomain.cpf.numero)
            .ifPresent {
                throw RecursoJaExisteException(
                    String.format("CPF %s já está cadastrado", clienteDomain.cpf.numero)
                )
            }

        return clienteRepository.salvar(clienteDomain)
    }

}
