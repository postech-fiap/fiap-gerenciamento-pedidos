package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.cliente

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

fun interface BuscarClientePorIdUseCase {
    fun executar(id: Long): Cliente
}