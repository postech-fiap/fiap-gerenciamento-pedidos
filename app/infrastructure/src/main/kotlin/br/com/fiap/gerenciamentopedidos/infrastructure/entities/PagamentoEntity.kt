package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
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
    val status: PagamentoStatus? = null,

    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    val pedido: PedidoEntity? = null

) {
    fun toDto() = PagamentoDto(id, dataHora!!, status!!)

    fun fromDto(pagamento: PagamentoDto) = PagamentoEntity(
        id = pagamento.id,
        dataHora = pagamento.dataHora,
        status = pagamento.status,
        pedido = pedido
    )
}
