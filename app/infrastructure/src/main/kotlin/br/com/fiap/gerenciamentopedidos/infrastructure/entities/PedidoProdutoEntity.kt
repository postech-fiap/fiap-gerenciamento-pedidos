package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.models.PedidoProduto
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "pedido_produto")
data class PedidoProdutoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    val pedido: PedidoEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    val produto: ProdutoEntity? = null,

    @Column(name = "valor_pago", nullable = false)
    val valorPago: BigDecimal? = null,

    @Column(name = "quantidade", nullable = false)
    val quantidade: Int? = null,

    @Column(name = "comentario", nullable = true)
    val comentario: String? = null,

) {

    fun toDomain(): PedidoProduto {
        return PedidoProduto(
            id = id,
            pedido = null,
            produto = produto!!.toDomain(),
            valorPago = valorPago!!,
            quantidade = quantidade!!,
            comentario = comentario
        )
    }

}
