package br.com.fiap.gerenciamentopedidos.application.controllers

import br.com.fiap.gerenciamentopedidos.application.interfaces.ClienteController
import br.com.fiap.gerenciamentopedidos.application.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.application.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.CadastrarClienteUseCase

class ClienteControllerImpl(
    private val cadastrarClienteUseCase: CadastrarClienteUseCase,
    private val buscarClientePorCpfUseCase: BuscarClientePorCpfUseCase
) : ClienteController {
    override fun cadastrarCliente(request: CadastrarClienteRequest) =
        ClienteResponse(cadastrarClienteUseCase.executar(request.toModel()))

    override fun buscarClientePorCpf(cpf: String) = ClienteResponse(buscarClientePorCpfUseCase.executar(cpf))
}
