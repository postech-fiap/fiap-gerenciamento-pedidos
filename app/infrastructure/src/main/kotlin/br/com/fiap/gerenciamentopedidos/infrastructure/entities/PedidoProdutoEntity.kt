package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoProdutoDto
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "pedido_produto")
data class PedidoProdutoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false, insertable = false, updatable = false)
    val pedido: PedidoEntity? = null,

    @Column(name = "pedido_id")
    val pedidoId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false, insertable = false, updatable = false)
    val produto: ProdutoEntity? = null,

    @Column(name = "produto_id")
    val produtoId: Long? = null,

    @Column(name = "valor_pago", nullable = false)
    val valorPago: BigDecimal? = null,

    @Column(name = "quantidade", nullable = false)
    val quantidade: Long? = null,

    @Column(name = "comentario", nullable = true)
    val comentario: String? = null
) {
    fun toDto() = PedidoProdutoDto(id, produto!!.toDto(), quantidade!!, comentario)

    companion object {
        fun fromDto(pedidoProduto: PedidoProdutoDto) = PedidoProdutoEntity(
            produto = ProdutoEntity.fromDto(pedidoProduto.produto),
            valorPago = pedidoProduto.valorPago,
            quantidade = pedidoProduto.quantidade,
            comentario = pedidoProduto.comentario
        )
    }
}
