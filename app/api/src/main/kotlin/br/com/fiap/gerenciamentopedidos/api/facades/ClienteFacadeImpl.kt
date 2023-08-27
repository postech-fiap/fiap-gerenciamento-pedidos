package br.com.fiap.gerenciamentopedidos.api.facades

import br.com.fiap.gerenciamentopedidos.api.facades.interfaces.ClienteFacade
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.api.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.CadastrarClienteUseCase

class ClienteFacadeImpl(
    private val cadastrarClienteUseCase: CadastrarClienteUseCase,
    private val buscarClientePorCpfUseCase: BuscarClientePorCpfUseCase
) : ClienteFacade {
    override fun cadastrarCliente(request: CadastrarClienteRequest) =
        ClienteResponse(cadastrarClienteUseCase.executar(request.toModel()))

    override fun buscarClientePorCpf(cpf: String) = ClienteResponse(buscarClientePorCpfUseCase.executar(cpf))
}
