package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

interface BuscarPedidosUseCase {
    fun executar(): List<Pedido>
}
