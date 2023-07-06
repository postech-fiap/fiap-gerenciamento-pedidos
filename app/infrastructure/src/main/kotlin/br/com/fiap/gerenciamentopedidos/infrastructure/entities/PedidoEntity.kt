package br.com.fiap.gerenciamentopedidos.infrastructure.entities

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
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
    val tempoEsperaMinutos: Long? = 0,

    @Column(name = "numero", nullable = false, length = 4)
    val numero: String? = null,

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    var cliente: ClienteEntity? = null,

    @Column(name = "cliente_id", insertable = false, updatable = false)
    val clienteId: Long? = null,

    @OneToMany(
        mappedBy = "pedido",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST],
        orphanRemoval = true
    )
    var produtos: List<PedidoProdutoEntity>? = null,

    @OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST], optional = true)
    var pagamento: PagamentoEntity? = null
) {
    fun toDto(): PedidoDto {
        val cliente = cliente?.toDto(cliente?.cpf!!)

        val produtos = produtos?.stream()
            ?.map { it.toDto() }
            ?.collect(Collectors.toList())

        return PedidoDto(
            id = id,
            dataHora = dataHora!!,
            status = status!!,
            tempoEsperaMinutos = tempoEsperaMinutos!!,
            numero = numero!!,
            cliente = cliente,
            produtos = produtos,
            pagamento = pagamento?.toDto()
        )
    }

    companion object {
        fun fromDto(pedido: PedidoDto): PedidoEntity {
            val entity = PedidoEntity(
                id = pedido.id,
                dataHora = pedido.dataHora,
                status = pedido.status,
                tempoEsperaMinutos = pedido.tempoEsperaMinutos,
                numero = pedido.numero,
                clienteId = pedido.cliente?.id,
                produtos = pedido.produtos?.map { PedidoProdutoEntity.fromDto(it) }
            )
            entity.pagamento = pedido.pagamento?.let { PagamentoEntity.fromDto(it, entity) }
            entity.produtos = pedido.produtos?.map {
                PedidoProdutoEntity(
                    pedido = entity,
                    produtoId = it.produto.id,
                    valorPago = it.valorPago,
                    quantidade = it.quantidade,
                    comentario = it.comentario
                )
            }
            return entity
        }
    }
}

