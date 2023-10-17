package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

fun interface BuscarPedidosUseCase {
    fun executar(): List<Pedido>
}
