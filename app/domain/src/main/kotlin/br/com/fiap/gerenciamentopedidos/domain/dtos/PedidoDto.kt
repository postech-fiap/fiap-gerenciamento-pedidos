package br.com.fiap.gerenciamentopedidos.domain.dtos

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.time.OffsetDateTime

data class PedidoDto(
    val id: Long? = null,
    val dataHora: OffsetDateTime? = null,
    val status: PedidoStatus? = null,
    val tempoEsperaMinutos: Long? = null,
    val numero: String? = null,
    val cliente: ClienteDto? = null,
    val produtos: List<PedidoProdutoDto>? = null,
    val pagamento: PagamentoDto? = null
) {
    companion object {
        fun fromModel(pedido: Pedido) = PedidoDto(
            pedido.id,
            pedido.dataHora,
            pedido.status,
            pedido.tempoEsperaMinutos,
            pedido.numero,
            pedido.cliente?.let { ClienteDto.fromModel(it) },
            pedido.produtos?.map { PedidoProdutoDto.fromModel(it) },
            pedido.pagamento?.let { PagamentoDto.fromModel(it) }
        )
    }
}
