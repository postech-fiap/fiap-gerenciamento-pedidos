package br.com.fiap.gerenciamentopedidos.domain.models

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import java.time.OffsetDateTime

data class Pedido(
    val id: Long? = null,
    val numero: String? = "1",
    val dataHora: OffsetDateTime = OffsetDateTime.now(),
    val status: PedidoStatus = PedidoStatus.RECEBIDO,
    var cliente: Cliente? = null,
    var items: List<Item> = listOf(),
    var pagamento: Pagamento? = null,
    var tempoEsperaMinutos: Long? = 0,
) {
    init {
        //require(items.isEmpty().not()) { "Ao menos um produto deve ser informado" }
        //require(PagamentoStatus.APROVADO == pagamento?.status) { "O pagamento deve estar aprovado para concluir o pedido" }
        //calcularTempoEspera()
        require(dataHora.isBefore(OffsetDateTime.now())) { "A data e hora do pedido deve ser menor ou igual que a data e hora atual" }
    }

    private fun calcularTempoEspera() {
        tempoEsperaMinutos = items.map { it.produto?.tempoPreparo }.maxBy { it!! }
    }

    fun atribuirCliente(cliente: Cliente) {
        this.cliente = cliente
    }

    fun adicionarItem(produto: Produto, quantidade: Long, comentario: String? = null) {
        items = items.plus(
            Item(
                quantidade = quantidade,
                comentario = comentario,
                produto = produto,
                valorPago = produto.valor
            )
        )
        calcularTempoEspera()
    }
}
