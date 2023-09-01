package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.time.OffsetDateTime

interface PedidoRepository {

    fun buscarPedidos(): List<Pedido>
    fun obterUltimoNumeroPedidoDoDia(): String
    fun salvar(pedido: Pedido): Pedido
    fun buscarPedidoPorId(id: Long): Pedido
    fun alterarStatusPedido(status: PedidoStatus, id: Long)
}
