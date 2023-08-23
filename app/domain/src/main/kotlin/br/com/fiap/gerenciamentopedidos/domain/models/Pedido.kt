package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.math.BigDecimal
import java.time.OffsetDateTime

data class Pedido(
    val id: Long? = null,
    val numero: String? = "1",
    val dataHora: OffsetDateTime = OffsetDateTime.now(),
    val status: PedidoStatus = PedidoStatus.RECEBIDO,
    var cliente: Cliente? = null,
    var produtos: List<PedidoProduto> = listOf(),
    var pagamento: Pagamento? = null,
    var tempoEsperaMinutos: Long? = 0,
    var valorTotal: BigDecimal? = null
) {
    init {
        require(produtos.isEmpty().not()) { "Ao menos um produto deve ser informado" }
        require(dataHora.isBefore(OffsetDateTime.now())) { "A data e hora do pedido deve ser menor ou igual que a data e hora atual" }
        calcularTempoEspera()
        calculateValorTotal()
    }

    private fun calcularTempoEspera() {
        tempoEsperaMinutos = produtos.map { it.produto?.tempoPreparo }.maxBy { it!! }
    }

    private fun calculateValorTotal() {
        valorTotal = produtos
            .map { it.valorPago }
            .fold(BigDecimal.ZERO, BigDecimal::add)
    }

}
