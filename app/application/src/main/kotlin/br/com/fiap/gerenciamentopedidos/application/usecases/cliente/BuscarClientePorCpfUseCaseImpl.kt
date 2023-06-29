package br.com.fiap.gerenciamentopedidos.application.usecases.cliente

import br.com.fiap.gerenciamentopedidos.application.interfaces.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.adapters.ClienteAdapter
import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

class BuscarClientePorCpfUseCaseImpl(val clienteAdapter: ClienteAdapter) : BuscarClientePorCpfUseCase {

    override fun executar(cpf: String): Cliente {
        return clienteAdapter.buscarPorCpf(cpf)
            .orElseThrow {
                RecursoNaoEncontradoException(
                    String.format("CPF %s não encontrado", cpf)
                )
            }
    }
}
