package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import jakarta.persistence.*
import java.time.OffsetDateTime

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
    val tempoEsperaMinutos: Long? = 0,

    @Column(name = "numero", nullable = false, length = 4)
    val numero: String? = null,

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    var cliente: ClienteEntity? = null,

    @OneToMany(
        mappedBy = "pedido",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST],
        orphanRemoval = true
    )
    var produtos: List<PedidoProdutoEntity>? = null,

    @OneToOne(
        mappedBy = "pedido",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var pagamento: PagamentoEntity? = null
) {
    fun toModel(): Pedido {
        val cliente = cliente?.toModel()

        val produtos = produtos?.map { it.toModel() }

        return Pedido(
            id = id,
            dataHora = dataHora!!,
            status = status!!,
            tempoEsperaMinutos = tempoEsperaMinutos!!,
            numero = numero!!,
            cliente = cliente,
            items = produtos!!,
            pagamento = pagamento?.toModel()
        )
    }

    companion object {
        fun fromModel(pedido: Pedido): PedidoEntity {
            val entity = PedidoEntity(
                id = pedido.id,
                dataHora = pedido.dataHora,
                status = pedido.status,
                tempoEsperaMinutos = pedido.tempoEsperaMinutos,
                numero = pedido.numero,
                cliente = pedido.cliente?.let { ClienteEntity.fromModel(it) }
            )
            entity.pagamento = pedido.pagamento.let { PagamentoEntity.fromModel(it!!, entity) }
            entity.produtos = pedido.items.map { PedidoProdutoEntity.fromModel(it, entity) }
            return entity
        }
    }
}

