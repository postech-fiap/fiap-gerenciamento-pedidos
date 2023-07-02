package br.com.fiap.gerenciamentopedidos.domain.usecases.cliente

import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ClientePort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente.BuscarClientePorCpfPort

class BuscarClientePorCpfUseCaseImpl(val clientePort: ClientePort) : BuscarClientePorCpfPort {
    override fun executar(cpf: String) = clientePort.buscarPorCpf(cpf)
        .orElseThrow { RecursoNaoEncontradoException(String.format("CPF %s não encontrado", cpf)) }
}
