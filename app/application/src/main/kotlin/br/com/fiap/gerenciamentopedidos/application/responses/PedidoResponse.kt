package br.com.fiap.gerenciamentopedidos.application.responses

import br.com.fiap.gerenciamentopedidos.domain.dtos.PedidoDto
import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.Pedido
import java.time.OffsetDateTime
import java.util.stream.Collectors

data class PedidoResponse(private val pedido: PedidoDto) {

    val id: Long
    val dataHora: OffsetDateTime
    val status: PedidoStatus
    val tempoEsperaMinutos: Int
    val numero: String
    val cliente: ClienteResponse?
    val produtos: List<PedidoProdutoResponse>?
    val pagamento: PagamentoResponse?

    init {

        var clienteResponse: ClienteResponse? = null
        if (pedido.cliente != null) {
            clienteResponse = ClienteResponse.fromDomain(pedido.cliente!!)
        }

        val produtosResponse = pedido.produtos?.stream()
            ?.map { PedidoProdutoResponse(it) }
            ?.collect(Collectors.toList())

        var pagamentoResponse: PagamentoResponse? = null
        if (pedido.pagamento != null) {
            pagamentoResponse = PagamentoResponse(pedido.pagamento!!)
        }

        id = pedido.id!!
        dataHora = pedido.dataHora!!
        status = pedido.status!!
        tempoEsperaMinutos = pedido.tempoEsperaMinutos!!
        numero = pedido.numero!!
        cliente = clienteResponse
        produtos = produtosResponse
        pagamento = pagamentoResponse

    }

}
