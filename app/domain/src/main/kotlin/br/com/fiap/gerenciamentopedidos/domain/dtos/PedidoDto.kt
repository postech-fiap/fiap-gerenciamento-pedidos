package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.time.OffsetDateTime

data class PedidoDto(
    val id: Long? = null,
    val numero: String? = null,
    val dataHora: OffsetDateTime? = null,
    val status: PedidoStatus? = null,
    val cliente: ClienteDto? = null,
    val produtos: List<PedidoProdutoDto>? = null,
    val pagamento: PagamentoDto? = null,
    val tempoEsperaMinutos: Long? = null
) {
    companion object {
        fun fromModel(pedido: Pedido) = PedidoDto(
            pedido.id,
            pedido.numero,
            pedido.dataHora,
            pedido.status,
            pedido.cliente?.let { ClienteDto.fromModel(it) },
            pedido.produtos.map { PedidoProdutoDto.fromModel(it) },
            pedido.pagamento?.let { PagamentoDto.fromModel(it) },
            pedido.tempoEsperaMinutos
        )
    }

    fun toModel() = Pedido(
        id,
        numero,
        dataHora!!,
        status!!,
        cliente?.toModel(),
        produtos!!.map { it.toModel() },
        pagamento?.toModel(),
        tempoEsperaMinutos
    )

}
