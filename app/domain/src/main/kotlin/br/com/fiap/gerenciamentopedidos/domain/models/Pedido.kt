package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.math.BigDecimal
import java.time.OffsetDateTime

data class Pedido(
    val id: Long? = null,
    val numero: String? = "1",
    val dataHora: OffsetDateTime = OffsetDateTime.now(),
    val status: PedidoStatus = PedidoStatus.PENDENTE,
    val clienteId: Long? = null,
    val produtos: List<PedidoProduto> = listOf(),
    var pagamento: Pagamento? = null,
    var valorTotal: BigDecimal? = BigDecimal.ZERO,
    var tempoEsperaMinutos: Long? = 0,
) {
    fun incluirPagamento(dataHora: OffsetDateTime, status: PagamentoStatus) {
        pagamento = Pagamento(
            dataHora = dataHora,
            status = status
        )
    }

    fun incluirProduto(produtoId: Long, quantidade: Long, tempoPreparo: Long, comentario: String) {
        produtos.plus(PedidoProduto(produtoId = produtoId, quantidade = quantidade, comentario = comentario))
        valorTotal = BigDecimal.valueOf(
            produtos.stream()
                .mapToDouble { it.produto?.valor?.multiply(BigDecimal.valueOf(it.quantidade))?.toDouble()!! }
                ?.sum()!!)
        tempoEsperaMinutos = if (tempoPreparo > tempoEsperaMinutos!!) tempoPreparo else tempoEsperaMinutos
    }
}
