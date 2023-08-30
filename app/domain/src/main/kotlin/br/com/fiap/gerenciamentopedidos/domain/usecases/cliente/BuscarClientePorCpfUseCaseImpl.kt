package br.com.fiap.gerenciamentopedidos.domain.usecases.cliente

import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository

class BuscarClientePorCpfUseCaseImpl(private val clienteRepository: ClienteRepository) : BuscarClientePorCpfUseCase {
    override fun executar(cpf: String) = clienteRepository.buscarPorCpf(cpf)
        .orElseThrow { RecursoNaoEncontradoException(String.format("CPF %s não encontrado", cpf)) }
}
