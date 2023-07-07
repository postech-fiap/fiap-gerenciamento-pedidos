package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto

class PedidoResponse(pedido: PedidoDto) {
    val id = pedido.id
    val dataHora = pedido.dataHora
    val status = pedido.status
    val tempoEsperaMinutos = pedido.tempoEsperaMinutos
    val numero = pedido.numero
    val cliente = pedido.cliente?.let { ClienteResponse(it) }
    val produtos = pedido.produtos?.stream()?.map { PedidoProdutoResponse(it) }
    val pagamento = pedido.pagamento?.let { PagamentoResponse(it) }
}
