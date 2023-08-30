package br.com.fiap.gerenciamentopedidos.domain.usecases.cliente

import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.BuscarClientePorIdUseCase
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository

class BuscarClientePorIdUseCaseImpl(private val clienteRepository: ClienteRepository) : BuscarClientePorIdUseCase {
    override fun executar(id: Long) = clienteRepository.buscarPorId(id)
        .orElseThrow { RecursoNaoEncontradoException("Cliente não encontrado") }
}
