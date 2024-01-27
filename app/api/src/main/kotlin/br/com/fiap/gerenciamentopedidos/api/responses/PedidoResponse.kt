package br.com.fiap.gerenciamentopedidos.api.responses

import br.com.fiap.gerenciamentopedidos.domain.enums.PagamentoStatus
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.time.OffsetDateTime

class PedidoResponse {
    val id: Long?
    val dataHora: OffsetDateTime?
    val status: PedidoStatus?
    val tempoEsperaMinutos: Long?
    val numero: String?
    val clienteId: String?
    val statusPagamento: PagamentoStatus?
    val infoPagamento: String?
    val produtos: List<PedidoProdutoResponse>?
    constructor(pedido: Pedido) {
        id = pedido.id
        dataHora = pedido.dataHora
        status = pedido.status
        tempoEsperaMinutos = pedido.tempoEsperaMinutos
        numero = pedido.numero
        clienteId = pedido.clienteId
        produtos = pedido.items.map { PedidoProdutoResponse(it) }
        statusPagamento = pedido.statusPagamento
        infoPagamento = pedido.infoPagamento
    }
}
