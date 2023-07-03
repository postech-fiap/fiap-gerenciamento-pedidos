package br.com.fiap.gerenciamentopedidos.domain.usecases.cliente

import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoNaoEncontradoException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ClientePort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente.BuscarClientePorCpf

class BuscarClientePorCpfImpl(private val clientePort: ClientePort) : BuscarClientePorCpf {
    override fun executar(cpf: String) = ClienteResponse(clientePort.buscarPorCpf(cpf)
        .orElseThrow { RecursoNaoEncontradoException(String.format("CPF %s não encontrado", cpf)) }
        .toModel()
    )
}
