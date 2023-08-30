package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

interface BuscarClientePorCpfUseCase {

    fun executar(cpf: String): Cliente
}