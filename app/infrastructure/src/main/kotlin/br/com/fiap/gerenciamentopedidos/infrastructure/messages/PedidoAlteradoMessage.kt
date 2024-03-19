package br.com.fiap.gerenciamentopedidos.infrastructure.messages

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.io.Serializable

data class PedidoAlteradoMessage(
    val idPedido: Long,
    val idCliente: String,
    val numeroPedido: String,
    val status: PedidoStatus,
) : Serializable {
    companion object {
        fun fromModel(pedido: Pedido) = PedidoAlteradoMessage(
            idPedido = pedido.id!!,
            idCliente = pedido.clienteId!!,
            numeroPedido = pedido.numero!!,
            status = pedido.status
        )
    }
}