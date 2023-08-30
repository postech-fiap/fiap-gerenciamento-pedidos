package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pagamento
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

    @Column(name = "qr_code", nullable = false)
    val qrCode: String? = null,

    @Column(name = "valor_total", nullable = false)
    val valorTotal: BigDecimal? = null,

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    val pedido: PedidoEntity? = null
) {
    fun toModel() = Pagamento(id, dataHora!!, status!!, qrCode!!, valorTotal!!)

    companion object {
        fun fromModel(pagamento: Pagamento, pedido: PedidoEntity) = PagamentoEntity(
            id = pagamento.id,
            dataHora = pagamento.dataHora,
            status = pagamento.status,
            qrCode = pagamento.qrCode,
            valorTotal = pagamento.valorTotal,
            pedido = pedido
        )
    }
}
