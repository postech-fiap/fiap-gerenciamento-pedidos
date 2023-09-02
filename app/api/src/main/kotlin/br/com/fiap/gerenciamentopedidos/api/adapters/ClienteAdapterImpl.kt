package br.com.fiap.gerenciamentopedidos.api.adapters

import br.com.fiap.gerenciamentopedidos.api.adapters.interfaces.ClienteAdapter
import br.com.fiap.gerenciamentopedidos.api.requests.CadastrarClienteRequest
import br.com.fiap.gerenciamentopedidos.api.responses.ClienteResponse
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.BuscarClientePorCpfUseCase
import br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente.CadastrarClienteUseCase

class ClienteAdapterImpl(
    private val cadastrarClienteUseCase: CadastrarClienteUseCase,
    private val buscarClientePorCpfUseCase: BuscarClientePorCpfUseCase
) : ClienteAdapter {
    override fun cadastrarCliente(request: CadastrarClienteRequest) =
        ClienteResponse(cadastrarClienteUseCase.executar(request.toModel()))

    override fun buscarClientePorCpf(cpf: String) = ClienteResponse(buscarClientePorCpfUseCase.executar(cpf))
}
