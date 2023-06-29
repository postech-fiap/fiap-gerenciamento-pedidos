package br.com.fiap.gerenciamentopedidos.application.usecases.cliente

import br.com.fiap.gerenciamentopedidos.application.interfaces.cliente.CadastrarClienteUseCase
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoJaExisteException
import br.com.fiap.gerenciamentopedidos.domain.adapters.ClienteAdapter
import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

class CadastrarClienteUseCaseImpl(val clienteAdapter: ClienteAdapter) : CadastrarClienteUseCase {

    override fun executar(clienteDomain: Cliente): Cliente {
        clienteAdapter.buscarPorCpf(clienteDomain.cpf.numero)
            .ifPresent {
                throw RecursoJaExisteException(
                    String.format("CPF %s já está cadastrado", clienteDomain.cpf.numero)
                )
            }

        return clienteAdapter.salvar(clienteDomain)
    }

}
