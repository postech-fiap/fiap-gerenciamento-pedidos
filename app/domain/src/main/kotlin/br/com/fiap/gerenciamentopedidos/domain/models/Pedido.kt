package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.math.BigDecimal
import java.time.OffsetDateTime

data class Pedido(
    val numero: String?,
    val id: Long? = null,
    val dataHora: OffsetDateTime = OffsetDateTime.now(),
    var status: PedidoStatus = PedidoStatus.RECEBIDO,
    var cliente: Cliente? = null,
    var items: List<Item> = listOf(),
    var pagamento: Pagamento? = null,
    var tempoEsperaMinutos: Long? = 0,
    var valorTotal: BigDecimal? = null
) {
    private fun calcularTempoEspera() {
        tempoEsperaMinutos = items.map { it.produto?.tempoPreparo }.maxBy { it!! }
    }

    fun atribuirCliente(cliente: Cliente) {
        this.cliente = cliente
    }

    fun adicionarItem(item: Item) {
        items = items.plus(item)
        calcularTempoEspera()
        calculateValorTotal()
    }

    fun adicionarItem(produto: Produto, quantidade: Long, comentario: String? = null) {
        adicionarItem(
            Item(
                quantidade = quantidade,
                comentario = comentario,
                produto = produto,
                valorPago = produto.valor
            )
        )
    }

    private fun calculateValorTotal() {
        valorTotal = items.map { it.valorPago }.fold(BigDecimal.ZERO, BigDecimal::add)
    }

    fun atribuirPagamento(pagamento: Pagamento) {
        this.pagamento = pagamento
    }

    fun alterarStatus(status: PedidoStatus) {
        this.status = status
    }
}
