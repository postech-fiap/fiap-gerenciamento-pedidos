package br.com.fiap.gerenciamentopedidos.infrastructure.messages

import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.io.Serializable
import java.time.OffsetDateTime

data class PedidoRecebidoMessage(
    val id: Long?,
    val number: String?,
    val createdDate: OffsetDateTime,
    var items: List<ItemPedidoRecebidoMessage>
) : Serializable {
    companion object {
        fun fromModel(pedido: Pedido) = PedidoRecebidoMessage(
            id = pedido.id,
            number = pedido.numero,
            createdDate = pedido.dataHora,
            items = pedido.items.map { ItemPedidoRecebidoMessage(it.produto?.nome, it.quantidade, it.comentario) }
        )
    }
}

data class ItemPedidoRecebidoMessage(
    val name: String?,
    val quantity: Long,
    val comment: String?
) : Serializable