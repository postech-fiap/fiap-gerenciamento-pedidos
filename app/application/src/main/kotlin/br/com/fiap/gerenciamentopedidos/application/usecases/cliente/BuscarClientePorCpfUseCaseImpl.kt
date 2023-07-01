package br.com.fiap.gerenciamentopedidos.application.usecases.cliente

import br.com.fiap.gerenciamentopedidos.application.interfaces.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.ClientePort
import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

class BuscarClientePorCpfUseCaseImpl(val clientePort: ClientePort) : BuscarClientePorCpfUseCase {

    override fun executar(cpf: String): Cliente {
        return clientePort.buscarPorCpf(cpf)
            .orElseThrow {
                RecursoNaoEncontradoException(
                    String.format("CPF %s não encontrado", cpf)
                )
            }
    }
}
