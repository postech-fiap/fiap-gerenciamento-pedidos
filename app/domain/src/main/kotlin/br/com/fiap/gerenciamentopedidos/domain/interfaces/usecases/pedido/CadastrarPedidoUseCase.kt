package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

fun interface CadastrarPedidoUseCase {
    fun executar(clienteId: String?, itens: List<Item>): Pedido
}
