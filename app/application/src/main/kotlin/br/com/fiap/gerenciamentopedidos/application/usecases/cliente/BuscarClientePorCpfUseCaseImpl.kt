package br.com.fiap.gerenciamentopedidos.application.usecases.cliente

import br.com.fiap.gerenciamentopedidos.application.interfaces.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.application.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.ClientePort

class BuscarClientePorCpfUseCaseImpl(val clientePort: ClientePort) : BuscarClientePorCpfUseCase {
    override fun executar(cpf: String) = ClienteResponse.fromDomain(clientePort.buscarPorCpf(cpf)
        .orElseThrow { RecursoNaoEncontradoException(String.format("CPF %s não encontrado", cpf)) })
}
