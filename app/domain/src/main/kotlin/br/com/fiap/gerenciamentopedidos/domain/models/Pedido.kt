package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.time.OffsetDateTime

data class Pedido(
    val id: Long? = null,
    val dataHora: OffsetDateTime,
    val status: PedidoStatus,
    val tempoEsperaMinutos: Int,
    val numero: String,
    val cliente: Cliente? = null,
    val produtos: List<PedidoProduto>? = null,
    val pagamento: Pagamento? = null
)
