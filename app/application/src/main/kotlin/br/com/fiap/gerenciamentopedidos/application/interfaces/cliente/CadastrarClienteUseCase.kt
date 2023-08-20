package br.com.fiap.gerenciamentopedidos.application.interfaces.cliente

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

interface CadastrarClienteUseCase {

    fun executar(request: Cliente): Cliente
}