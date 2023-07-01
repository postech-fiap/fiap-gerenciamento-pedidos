package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "pagamento")
data class PagamentoEntity(

    @Id
    val id: Long? = null,

    @Column(name = "data_hora", nullable = false)
    val dataHora: OffsetDateTime? = null,

    @Column(name = "status", nullable = false)
    val status: String? = null,

    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    val pedido: PedidoEntity? = null

) {

    fun toDomain(): Pagamento {
        return Pagamento(
            id = id,
            dataHora = dataHora!!,
            status = status!!,
            pedido = null
        )
    }

}
