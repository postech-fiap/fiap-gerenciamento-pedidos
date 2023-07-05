package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.time.OffsetDateTime

data class Pedido(
    val id: Long? = null,
    val numero: String? = "1",
    val dataHora: OffsetDateTime = OffsetDateTime.now(),
    val status: PedidoStatus = PedidoStatus.PENDENTE,
    val clienteId: Long? = null,
    val produtos: List<PedidoProduto>?,
    var tempoEsperaMinutos: Long? = 0,
    var pagamento: Pagamento? = null
) {
    init {
        tempoEsperaMinutos = 0
    }

    fun incluirPagamento(dataHora: OffsetDateTime, status: PagamentoStatus) {
        pagamento = Pagamento(
            dataHora = dataHora,
            status = status
        )
    }

    fun getValorTotal() = produtos?.stream()?.mapToDouble { it.getValorTotal() }?.sum()
}
