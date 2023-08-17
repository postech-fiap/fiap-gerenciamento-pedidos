package br.com.fiap.gerenciamentopedidos.application.interfaces.cliente

import br.com.fiap.gerenciamentopedidos.domain.models.Cliente

interface BuscarClientePorIdUseCase {
    fun executar(id: Long): Cliente
}