package br.com.fiap.gerenciamentopedidos.domain.usecases.cliente

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import br.com.fiap.gerenciamentopedidos.domain.dtos.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.domain.dtos.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoJaExisteException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ClientePort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente.CadastrarCliente

class CadastrarClienteUseCaseImpl(private val clientePort: ClientePort) : CadastrarCliente {

    override fun executar(request: CadastrarClienteRequest): ClienteResponse {
        clientePort.buscarPorCpf(request.cpf)
            .ifPresent { throw RecursoJaExisteException(String.format("CPF %s já está cadastrado", request.cpf)) }

        return ClienteResponse(clientePort.salvar(ClienteDto.fromModel(request.toModel())).toModel())
    }
}
