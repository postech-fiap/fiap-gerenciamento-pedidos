package br.com.fiap.gerenciamentopedidos.application.usecases.cliente

import br.com.fiap.gerenciamentopedidos.application.interfaces.cliente.CadastrarClienteUseCase
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoJaExisteException
import br.com.fiap.gerenciamentopedidos.domain.interfaces.ClienteRepository

class CadastrarClienteUseCaseImpl(private val clienteRepository: ClienteRepository) : CadastrarClienteUseCase {

    override fun executar(request: CadastrarClienteRequest): ClienteResponse {
        clienteRepository.buscarPorCpf(request.cpf)
            .ifPresent {
                throw RecursoJaExisteException(String.format("CPF %s já está cadastrado", request.cpf))
            }

        return ClienteResponse(clienteRepository.salvar(request.toModel()))
    }
}
