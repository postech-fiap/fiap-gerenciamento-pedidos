package br.com.fiap.gerenciamentopedidos.domain.interfaces.usecases.pedido

import br.com.fiap.gerenciamentopedidos.domain.models.Item
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

interface CadastrarPedidoUseCase {
    fun executar(clienteId: Long?, itens: List<Item>): Pedido
}
