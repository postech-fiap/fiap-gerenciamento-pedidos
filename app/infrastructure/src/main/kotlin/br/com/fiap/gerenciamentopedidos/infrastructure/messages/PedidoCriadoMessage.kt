package br.com.fiap.gerenciamentopedidos.infrastructure.messages

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.io.Serializable
import java.math.BigDecimal
import java.time.OffsetDateTime

data class PedidoCriadoMessage(
    val id: String? = null,
    val idPedido: Long?,
    val idCliente: String?,
    val numeroPedido: String?,
    val dataHora: OffsetDateTime?,
    val valorTotal: BigDecimal?,
    val items: List<ItemPedidoCriadoMessage>?,
    val status: PagamentoStatus? = null,
    val qrCode: String? = null
) : Serializable {
    companion object {
        fun fromModel(pedido: Pedido) = PedidoCriadoMessage(
            idPedido = pedido.id,
            idCliente = pedido.clienteId,
            numeroPedido = pedido.numero,
            dataHora = pedido.dataHora,
            valorTotal = pedido.valorTotal,
            items = pedido.items.map {
                ItemPedidoCriadoMessage(
                    quantidade = it.quantidade,
                    valorPago = it.valorPago,
                    produto = ProdutoPedidoCriadoMessage(
                        id = it.produto?.id,
                        nome = it.produto?.nome,
                        descricao = it.produto?.descricao,
                        categoria = it.produto?.categoria.toString(),
                        valor = it.produto?.valor,
                    )
                )
            }
        )
    }
}

data class ItemPedidoCriadoMessage(
    val quantidade: Long?,
    val valorPago: BigDecimal?,
    val produto: ProdutoPedidoCriadoMessage,
) : Serializable

data class ProdutoPedidoCriadoMessage(
    val id: Long?,
    val nome: String?,
    val descricao: String?,
    val categoria: String?,
    val valor: BigDecimal?,
) : Serializable