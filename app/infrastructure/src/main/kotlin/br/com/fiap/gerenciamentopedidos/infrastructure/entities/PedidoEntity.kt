package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.stream.Collectors

@Entity
@Table(name = "pedido")
data class PedidoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "data_hora", nullable = false)
    val dataHora: OffsetDateTime? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: PedidoStatus? = null,

    @Column(name = "tempo_espera_minutos", nullable = false)
    val tempoEsperaMinutos: Int? = 0,

    @Column(name = "numero", nullable = false, length = 4)
    val numero: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "cliente_id", nullable = true)
    val cliente: ClienteEntity? = null,

    @OneToMany(
        mappedBy = "pedido",
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    val produtos: List<PedidoProdutoEntity>? = null,

    @OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY, optional = true)
    val pagamento: PagamentoEntity? = null
) {
    fun toDto() = PedidoDto(
        id,
        dataHora!!,
        status!!,
        tempoEsperaMinutos!!,
        numero!!,
        cliente?.toDto(cliente.cpf!!),
        produtos?.stream()?.map { it.toDto() }?.collect(Collectors.toList()),
        pagamento?.toDto()
    )

    companion object {
        fun fromDto(pedido: PedidoDto) =
            PedidoEntity(pedido.id, pedido.dataHora, pedido.status, pedido.tempoEsperaMinutos, pedido.numero)
    }
}

