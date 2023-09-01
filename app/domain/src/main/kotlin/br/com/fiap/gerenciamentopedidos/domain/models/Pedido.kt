package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.interfaces.Model
import java.math.BigDecimal
import java.time.OffsetDateTime

data class Pedido(
    val numero: String?,
    val id: Long? = null,
    val dataHora: OffsetDateTime = OffsetDateTime.now(),
    var status: PedidoStatus = PedidoStatus.PENDENTE,
    var cliente: Cliente? = null,
    var items: List<Item> = listOf(),
    var pagamento: Pagamento? = null,
    var tempoEsperaMinutos: Long? = 0,
    var valorTotal: BigDecimal? = null
) : Model {
    private fun calcularTempoEspera() {
        tempoEsperaMinutos = items.map { it.produto?.tempoPreparo }.maxBy { it!! }
    }

    fun atribuirCliente(cliente: Cliente) {
        this.cliente = cliente
    }

    fun adicionarItem(item: Item) {
        items = items.plus(item.valid())
        calcularTempoEspera()
        calcularValorTotal()
    }

    fun adicionarItem(produto: Produto, quantidade: Long, comentario: String? = null) {
        adicionarItem(
            Item(
                quantidade = quantidade,
                comentario = comentario,
                produto = produto,
                valorPago = produto.valor * quantidade.toBigDecimal()
            )
        )
    }

    private fun calcularValorTotal() {
        valorTotal = items.map { it.valorPago }.fold(BigDecimal.ZERO, BigDecimal::add)
    }

    fun gerarQrCodePagamento(pagamento: Pagamento) {
        this.pagamento = pagamento
    }

    fun alterarStatus(status: PedidoStatus) {
        this.status = status
    }

    override fun valid(): Pedido {
        require(items.isEmpty().not()) { "Ao menos um produto deve ser informado" }
        require(dataHora.isBefore(OffsetDateTime.now())) { "A data e hora do pedido deve ser menor ou igual que a data e hora atual" }
        return this
    }
}
