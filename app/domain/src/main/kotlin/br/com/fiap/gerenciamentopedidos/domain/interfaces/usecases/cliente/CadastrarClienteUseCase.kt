package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

fun interface CadastrarClienteUseCase {

    fun executar(cliente: Cliente): Cliente
}