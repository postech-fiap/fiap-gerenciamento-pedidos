package br.com.fiap.gerenciamentopedidos.application.requests

import br.com.fiap.gerenciamentopedidos.domain.enums.PedidoStatus
import br.com.fiap.gerenciamentopedidos.domain.models.*
import java.time.OffsetDateTime

class CadastrarPedidoRequest(
    val clienteId: Long,
    val produtos: List<Produto>
) {
    fun toPedido(produtos: List<Produto>,
                 tempoEspera: Int,
                 numero: String,
                 cliente: Cliente,
                 pagamento: Pagamento) = Pedido(
        dataHora = OffsetDateTime.now(),
        status = PedidoStatus.PENDENTE,
        tempoEsperaMinutos = tempoEspera,
        numero = numero,
        cliente = cliente,
        produtos = null,
        pagamento = pagamento
    )
}
