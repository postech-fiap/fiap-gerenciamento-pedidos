package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.dtos.PagamentoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "pagamento")
data class PagamentoEntity(

    @Id
    val id: Long? = null,

    @Column(name = "data_hora", nullable = false)
    val dataHora: OffsetDateTime? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: PagamentoStatus? = null,

    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    val pedido: PedidoEntity? = null,

    @Column(name = "qr_code", nullable = false)
    val qrCode: String? = null,

    @Column(name = "valor_total", nullable = false)
    val valorTotal: BigDecimal? = null
) {
    fun toDto() = PagamentoDto(id, dataHora!!, status!!, qrCode, valorTotal)

    companion object {
        fun fromDto(pagamento: PagamentoDto, pedido: PedidoEntity) = PagamentoEntity(
            id = pagamento.id,
            dataHora = pagamento.dataHora,
            status = pagamento.status,
            pedido = pedido,
            qrCode = pagamento.qrCode,
            valorTotal = pagamento.valorTotal
        )
    }
}
