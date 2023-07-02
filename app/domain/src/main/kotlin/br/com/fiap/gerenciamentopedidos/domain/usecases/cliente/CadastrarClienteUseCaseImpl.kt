package br.com.fiap.gerenciamentopedidos.domain.usecases.cliente

import br.com.fiap.gerenciamentopedidos.domain.dtos.ClienteDto
import br.com.fiap.gerenciamentopedidos.domain.exceptions.RecursoJaExisteException
import br.com.fiap.gerenciamentopedidos.domain.ports.drivens.ClientePort
import br.com.fiap.gerenciamentopedidos.domain.ports.drivings.cliente.CadastrarClientePort

class CadastrarClienteUseCaseImpl(val clientePort: ClientePort) : CadastrarClientePort {

    override fun executar(cliente: ClienteDto): ClienteDto {
        clientePort.buscarPorCpf(cliente.cpf!!.numero)
            .ifPresent {
                throw RecursoJaExisteException(
                    String.format(
                        "CPF %s já está cadastrado",
                        cliente.cpf.numero
                    )
                )
            }

        return clientePort.salvar(cliente)
    }
}
