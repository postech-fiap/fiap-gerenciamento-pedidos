package br.com.fiap.gerenciamentopedidos.domain.interfaces

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.time.OffsetDateTime
import java.util.*

interface PedidoRepository {

    fun buscarPedidos(): List<Pedido>
    fun obterUltimoNumeroPedidoDoDia(): String
    fun salvar(pedido: Pedido): Pedido
    fun buscarPedidoPorId(id: Long): Optional<Pedido>
    fun alterarStatusPedido(status: PedidoStatus, id: Long)
}
