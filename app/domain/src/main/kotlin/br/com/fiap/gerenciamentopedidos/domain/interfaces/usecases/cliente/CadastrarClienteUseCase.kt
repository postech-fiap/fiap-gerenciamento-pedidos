package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

interface CadastrarClienteUseCase {

    fun executar(request: Cliente): Cliente
}