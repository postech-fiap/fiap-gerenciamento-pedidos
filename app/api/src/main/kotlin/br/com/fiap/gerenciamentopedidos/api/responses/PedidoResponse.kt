package br.com.fiap.gerenciamentopedidos.api.responses

import br.com.fiap.gerenciamentopedidos.domain.models.Pedido

class PedidoResponse(pedido: Pedido) {
    val id = pedido.id
    val dataHora = pedido.dataHora
    val status = pedido.status
    val tempoEsperaMinutos = pedido.tempoEsperaMinutos
    val numero = pedido.numero
    val cliente = pedido.cliente?.let { ClienteResponse(it) }
    val produtos = pedido.items.map { PedidoProdutoResponse(it) }
    val pagamento = pedido.pagamento?.let { PagamentoResponse(it) }
}
