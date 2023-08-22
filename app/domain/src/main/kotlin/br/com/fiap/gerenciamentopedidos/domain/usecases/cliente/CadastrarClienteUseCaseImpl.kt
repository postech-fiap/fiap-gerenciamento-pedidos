package br.com.fiap.gerenciamentopedidos.domain.usecases.cliente

import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.CadastrarClienteUseCase
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoJaExisteException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository
import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

class CadastrarClienteUseCaseImpl(private val clienteRepository: ClienteRepository) : CadastrarClienteUseCase {
    override fun executar(cliente: Cliente): Cliente {
        clienteRepository.buscarPorCpf(cliente.cpf!!.numero)
            .ifPresent {
                throw RecursoJaExisteException(String.format("CPF %s já está cadastrado", cliente.cpf))
            }

        return clienteRepository.salvar(cliente)
    }
}
